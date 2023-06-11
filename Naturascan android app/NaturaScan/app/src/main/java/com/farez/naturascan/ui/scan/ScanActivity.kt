package com.farez.naturascan.ui.scan

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.farez.naturascan.R
import com.farez.naturascan.databinding.ActivityScanBinding
import com.farez.naturascan.databinding.DialogScanPopupBinding
import com.farez.naturascan.ml.ConvertedModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.util.*
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.ops.ResizeOp

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val imageProcessor = ImageProcessor.Builder()
        .add(NormalizeOp(0.0f, 255.0f))
        .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.BILINEAR))
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        requestPermission()

        binding.cameraButton.setOnClickListener {
            takePhoto()
        }
        binding.galleryButton.setOnClickListener {
            val intent = Intent()
            intent.apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
            }
            val chooser = Intent.createChooser(intent, "pilih gambar")
            launcherIntentGallery.launch(chooser)
        }
    }

    fun createDialog(plantName : String) {
        val dialogBinding = DialogScanPopupBinding.inflate(layoutInflater)
        val dialog = Dialog(this@ScanActivity)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.apply {
            setBackgroundDrawableResource(R.color.transparent)
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialogBinding.apply {
            closeButton.setOnClickListener {
                dialog.dismiss()
            }
            saveButton.setOnClickListener {
                Toast.makeText(this@ScanActivity, "Disimpan ke Database", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            //SET PLANT NAME AND SAMPLE PLANT PICTURE IN DIALOG
            binding.apply {
                when (plantName) {
                    "Allium" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.allium)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Allium"
                    }
                    "Borage" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.borage)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Borage"

                    }
                    "Burdock" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.burdock)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Burdock"

                    }
                    "Calendula" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.calendula)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Calendula"

                    }
                    "Chickweed" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.chickweed)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Chickweed"
                    }
                    "Chicory" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.chicory)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Chicory"

                    }
                    "Common yarrow" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.daun_seribu)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Daun Seribu"

                    }
                    "Daisy" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.daisy)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Daisy"

                    }
                    "Dandelion" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.dandelion)
                            .centerCrop()
                            .into(edibilityImage)
                        edibilityImage.setImageResource(R.drawable.dandelion)
                        plantNameTextVIew.text = "Dandelion"

                    }
                    "Geranium" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.geranium)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Geranium"

                    }
                    "Ground ivy" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.glechoma_hederacea)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Glechoma Hederacea"

                    }
                    "Henbit" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.henbit)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Henbit"

                    }
                    "Meadowsweet" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.filipendula_ulmaria)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Filipendula Ulmaria"
                    }
                    "Ramsons" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.allium_ursinum)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Allium Ursinum"
                    }
                    "Red Clover" -> {
                        Glide.with(this@ScanActivity)
                            .load(R.drawable.semanggi_merah)
                            .centerCrop()
                            .into(edibilityImage)
                        plantNameTextVIew.text = "Semanggi Merah"
                    }
                    else -> println("Unknown plant.")
                }
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
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {
                startCamera()
            }
        }

    //function to identify image
    fun identifyImage(bitmap: Bitmap) {
            val shape = intArrayOf(1, 150, 150, 3)
            val model = ConvertedModel.newInstance(this@ScanActivity)
            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)
            tensorImage = imageProcessor.process(tensorImage)
            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(shape, DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            val labels = arrayOf(
                "Allium", "Borage", "Burdock",
                "Calendula", "Chickweed", "Chicory",
                "Common yarrow", "Daisy", "Dandelion",
                "Geranium", "Ground ivy", "Henbit",
                "Meadowsweet", "Ramsons", "Red Clover")
            var maxPos = 0
            confidences.forEachIndexed { index, fl ->
                if (confidences[maxPos] < fl) {
                    maxPos = index
                }
            }
        createDialog(labels[maxPos])
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
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    val bitmap = image.convertImageProxyToBitmap()
                    identifyImage(bitmap)
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@ScanActivity, "Error: ${exception.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            })
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@ScanActivity, "Error: ${exc.message}", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        this@ScanActivity, "Tersimpan di ${output.savedUri?.path}", Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onImageSaved: ${output.savedUri}")
                }
            })
    }

    fun ImageProxy.convertImageProxyToBitmap(): Bitmap {
        val buffer = planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val source = ImageDecoder.createSource(this.contentResolver, selectedImg)
            val bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
            identifyImage(bitmap)
        }
    }

    fun createFile(application: Application): File {
        val filenameFormat = "yyyy-MM-dd-HH-mm-ss"
        val timeStamp: String = SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis())
        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory =
            if (mediaDir != null && mediaDir.exists()) mediaDir else application.filesDir

        return File(outputDirectory, "NaturaScan$timeStamp.jpg")
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private val TAG = "loog tag"
    }
}