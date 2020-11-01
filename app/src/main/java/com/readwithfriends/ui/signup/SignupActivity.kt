package com.readwithfriends.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import com.readwithfriends.R
import com.readwithfriends.extensions.convertBase64
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.observe
import com.readwithfriends.viewmodel.AuthenticationViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.layout_book_image_list_item.view.*

class SignupActivity : CommonLoginSignUpActivity() {

    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SignupActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Finishes all previous activities
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val activity = this@SignupActivity
        getViewModel(AuthenticationViewModel::class.java) {
            authenticationState.observe(activity) {
                handleAuthenticationState(it)
            }
            button.setOnClickListener {
                this.signUp(email.text.toString(), password.text.toString(),nickName.text.toString(),name.text.toString(),profilePictureTiny.drawable.toBitmap().convertBase64())
            }

            profilePictureButton.setOnClickListener{
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode.equals(REQUEST_SELECT_IMAGE_IN_ALBUM) && resultCode == Activity.RESULT_OK){
            var photoUri = data?.data
            Picasso.get().load(photoUri).resize(250,250).into(profilePictureTiny);
        }
    }
}