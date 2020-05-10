package com.readwithfriends.model

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object CameraRepository {

    private const val PERMISSION_REQUEST_CODE: Int = 101
    private const val REQUEST_IMAGE_CAPTURE = 1

    fun takePicture(context : Context, photoFile : File?,activity: Activity) {

        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoURI = photoFile?.let {
            FileProvider.getUriForFile(
                context,
                "com.readwithfriends.fileprovider",
                it
            )
        }
        if(photoFile!=null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        activity.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    fun checkPermission(context : Context): Boolean {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestPermission(activity : Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }
}