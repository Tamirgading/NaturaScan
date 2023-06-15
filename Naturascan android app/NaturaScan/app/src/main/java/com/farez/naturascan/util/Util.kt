package com.farez.naturascan.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


fun reduceFileImage(bitmap: Bitmap): Bitmap {
    val bmpStream = ByteArrayOutputStream()
    return if (bmpStream.toByteArray().size > 1024000) {
        val compressQuality = 50
        bitmap.compress(Bitmap.CompressFormat.PNG, compressQuality, bmpStream)
        BitmapFactory.decodeStream(ByteArrayInputStream(bmpStream.toByteArray()))
    } else {
        bitmap
    }
}



