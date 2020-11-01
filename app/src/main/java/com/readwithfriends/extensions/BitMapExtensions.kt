package com.readwithfriends.extensions

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap.convertBase64() : String{
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 10, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}