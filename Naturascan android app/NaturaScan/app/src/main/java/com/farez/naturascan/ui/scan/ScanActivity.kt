package com.farez.naturascan.ui.scan

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.farez.naturascan.R
import com.farez.naturascan.app.App
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.databinding.ActivityScanBinding
import com.farez.naturascan.databinding.DialogScanPopupBinding
import com.farez.naturascan.di.Injection
import com.farez.naturascan.ml.ConvertedModel
import com.farez.naturascan.util.reduceFileImage
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.util.*

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private lateinit var viewModel: ScanViewModel
    private lateinit var vmFactory: ScanVMFactory
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(0.0f, 255.0f))
        .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.BILINEAR))
        .build()
    private val labels = arrayOf(
        "Allium", "Borage", "Burdock",
        "Calendula", "Chickweed", "Chicory",
        "Common yarrow", "Daisy", "Dandelion",
        "Geranium", "Ground ivy", "Henbit",
        "Meadowsweet", "Ramsons", "Red Clover"
    )
    private val sampleImageArray = arrayOf(
        R.drawable.allium, R.drawable.borage, R.drawable.burdock, R.drawable.calendula,
        R.drawable.chickweed, R.drawable.chicory, R.drawable.daun_seribu, R.drawable.daisy,
        R.drawable.dandelion, R.drawable.geranium, R.drawable.glechoma_hederacea, R.drawable.henbit,
        R.drawable.filipendula_ulmaria, R.drawable.allium_ursinum,  R.drawable.semanggi_merah

    )
    private val desc = App.resourses.getStringArray(R.array.plantDesc)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        requestPermission()
        setupViewModel()
        binding.cameraButton.setOnClickListener {
            takePhoto()
        }
        binding.galleryButton.setOnClickListener {
            val intent = Intent()
            intent.apply {
                type = "image/*"
                action = Intent.ACTION_OPEN_DOCUMENT
            }
            val chooser = Intent.createChooser(intent, "pilih gambar")
            launcherIntentGallery.launch(chooser)
        }
    }

    private fun setupViewModel() {
        vmFactory = ScanVMFactory(Injection.providePlantRepository(application))
        viewModel = ViewModelProvider(this@ScanActivity, vmFactory)[ScanViewModel::class.java]
    }

    private fun createDialog(plantName: String, imageUri: Uri) {
        var plantDesc : String? = null
        val dialogBinding = DialogScanPopupBinding.inflate(layoutInflater)
        val dialog = Dialog(this@ScanActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.apply {
            setBackgroundDrawableResource(R.color.transparent)
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialogBinding.apply {
            //SET PLANT NAME AND SAMPLE PLANT PICTURE IN DIALOG
            for (i in 0..labels.size) {
                if (plantName == labels[i]) {
                    Glide.with(this@ScanActivity)
                        .load(sampleImageArray[i])
                        .centerCrop()
                        .into(edibilityImage)
                    plantNameTextVIew.text = labels[i]
                    plantDesc = desc[i].toString()
                    break
                }
            }
            closeButton.setOnClickListener {
                dialog.dismiss()
            }
            saveButton.setOnClickListener {
                val plant = Plant(
                    name = plantNameTextVIew.text.toString(),
                    photoUri = imageUri.toString(),
                    desc = plantDesc
                )
                viewModel.insert(plant)
                Toast.makeText(this@ScanActivity, "Disimpan ke Database", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun requestPermission() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }

    //function to identify image
    fun identifyImage(bitmap: Bitmap, imageUri: Uri) {
        val shape = intArrayOf(1, 150, 150, 3)
        val model = ConvertedModel.newInstance(this@ScanActivity)
        val compressedBitmap = reduceFileImage(bitmap)
        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(compressedBitmap)
        tensorImage = imageProcessor.process(tensorImage)
        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(shape, DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.floatArray
        var maxPos = 0
        confidences.forEachIndexed { index, fl ->
            if (confidences[maxPos] < fl) {
                maxPos = index
            }
        }
        createDialog(labels[maxPos], imageUri)
        // Releases model resources if no longer used.
        model.close()
    }

    // Function to show preview from camera
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val viewFinder = binding.viewFinder
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(256, 256))
                .build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@ScanActivity, "Error: ${exc.message}", Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    //function to capture image from camera
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = this.createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@ScanActivity, "Error: ${exc.message}", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val uri = output.savedUri as Uri
                    val source = ImageDecoder.createSource(
                        this@ScanActivity.contentResolver,
                        output.savedUri!!
                    )
                    val bitmap =
                        ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
                    identifyImage(bitmap, uri)
                }
            })
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val source = ImageDecoder.createSource(this.contentResolver, selectedImg)
            val bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
            val uri = result.data?.data!!
            this.contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            identifyImage(bitmap, uri)
        }
    }

    private fun createFile(application: Application): File {
        val filenameFormat = "yyyy-MM-dd-HH-mm-ss"
        val timeStamp: String =
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory =
            if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir

        return File(outputDirectory, "NaturaScan$timeStamp.jpg")
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}