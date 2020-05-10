package com.readwithfriends.ui.registerbooks

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.readwithfriends.R
import com.readwithfriends.extensions.visible
import com.readwithfriends.model.CameraRepository
import kotlinx.android.synthetic.main.activity_registerbook.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class RegisterBookActivity :AppCompatActivity() {

    private var currentPhotoPath: String? = null;
    private  val PERMISSION_REQUEST_CODE: Int = 101
    private  val REQUEST_IMAGE_CAPTURE = 1
    private var fileInformation = ""

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, RegisterBookActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Finishes all previous activities
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registerbook)
        val activity = this@RegisterBookActivity

        takePhotoOfBook.setOnClickListener() {
            if (CameraRepository.checkPermission(this)){
                takePicture()
            } else CameraRepository.requestPermission(this@RegisterBookActivity)
        }

        retrieveBook.setOnClickListener(){
            DetailBookActivity.fileInformation = fileInformation
            val intent = Intent(this@RegisterBookActivity,DetailBookActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun takePicture(){
        val photoFile = createFile()
        CameraRepository.takePicture(this,photoFile,this@RegisterBookActivity)
    }

    @Throws(IOException::class)
    private fun createFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val photo: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!photo?.exists()!!) {
            photo?.mkdirs();
        }
        return File.createTempFile(
            "JPEG_${timeStamp}_algo", /* prefix */
            ".jpg", /* suffix */
            photo /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    takePicture()
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imgFile = File(currentPhotoPath)
            if (imgFile.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val b = BitmapFactory.decodeByteArray(imgFile.readBytes() , 0, imgFile.readBytes().size)
                    photoImage.setImageBitmap(Bitmap.createScaledBitmap(b, 250, 250, false));
                     fileInformation = Base64.getEncoder().encodeToString(imgFile.readBytes())
                    retrieveBook.visible()
                    imgFile.delete()
                }
            }
            Toast.makeText(this, "We did it", Toast.LENGTH_SHORT).show()
        }
    }
}
