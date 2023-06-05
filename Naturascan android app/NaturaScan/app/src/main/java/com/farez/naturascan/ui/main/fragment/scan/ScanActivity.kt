package com.farez.naturascan.ui.main.fragment.scan

import android.Manifest
import android.app.Application
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.farez.naturascan.R
import com.farez.naturascan.databinding.ActivityScanBinding
import com.farez.naturascan.databinding.DialogScanPopupBinding
import com.farez.naturascan.util.uriToFile
import java.io.File
import java.util.*

class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var file : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        requestPermission()

        binding.cameraButton.setOnClickListener {
            takePhoto()
            createDialog(false)
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

    fun createDialog(isEdible : Boolean) {
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
            }
            if(isEdible) {
                edibilityImage.setImageResource(R.drawable.edible_icon_svg)
                scanResultTextView.text = getString(R.string.resultEdible)
                scanResultTextView.setTextColor(ContextCompat.getColor(this@ScanActivity, R.color.textGreen))
                saveButton.setBackgroundColor(ContextCompat.getColor(this@ScanActivity, R.color.itemGreen))
                closeButton.setBackgroundColor(ContextCompat.getColor(this@ScanActivity, R.color.itemGreen))

            } else {
                edibilityImage.setImageResource(R.drawable.toxic_icon_svg)
                scanResultTextView.text = getString(R.string.resultToxic)
                scanResultTextView.setTextColor(ContextCompat.getColor(this@ScanActivity, R.color.toxicPurple))
                saveButton.setBackgroundColor(ContextCompat.getColor(this@ScanActivity, R.color.toxicPurple))
                closeButton.setBackgroundColor(ContextCompat.getColor(this@ScanActivity, R.color.toxicPurple))


            }
        }
        dialog.show()
    }

    private fun requestPermission() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
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

    // Function to show preview from camera
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder()
                .setTargetResolution(Size(512, 512))
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
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/NaturaScan")
            }
        }
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
                    Toast.makeText(
                        this@ScanActivity, "Tersimpan di ${output.savedUri?.path}", Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onImageSaved: ${output.savedUri}")
                }
            })
    }
    //function to upload image to cloud to be identified and show dialog on success
    private fun uploadImage() {
        //upload image to cloud using viewmodel
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ScanActivity)
            file = myFile
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

        return File(outputDirectory, "$timeStamp.jpg")
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private val TAG = "loog tag"
    }
}