package com.readwithfriends.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.observe
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.UserBooksBackendResponse
import com.readwithfriends.ui.registerbooks.RegisterBookActivity
import com.readwithfriends.ui.signup.LoginActivity
import com.readwithfriends.viewmodel.AuthenticationViewModel
import com.readwithfriends.viewmodel.UserDataViewModel
import com.readwithfriends.viewmodel.state.AuthenticationState
import com.readwithfriends.viewmodel.state.BookOperationsState
import com.readwithfriends.viewmodel.state.UsersOperationsState
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit  var booksRecovered : UserBooksBackendResponse

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            val activity = this@HomeActivity

        //comentario clave

        // UserDataViewModel to display user data
        getViewModel(UserDataViewModel::class.java) {
            email.observe(activity) {
                this.getUsers(it)
                this.getUserBooks(it)
            }

            getUsersOperationState.observe(activity){
                if(it is UsersOperationsState.Located){
                    var decodedPictureString: ByteArray =
                        Base64.decode(it.users[0].picture, Base64.DEFAULT)
                    imageView.setImageBitmap( BitmapFactory.decodeByteArray(decodedPictureString, 0, decodedPictureString.size))
                }
            }

            booksOperationState.observe(activity){
                if(it is BookOperationsState.LocatedBooks){
                    booksRecovered = it.books
                    showBooksList(booksRecovered.userBooks)
                }
            }
            /*email.observe(activity) {
                showUsername(it)
            }*/
        }

        // AuthenticationViewModel to handle the sign out event
        getViewModel(AuthenticationViewModel::class.java) {
            authenticationState.observe(activity) {
                handleAuthenticationState(it)
            }
            signOut.setOnClickListener {
                signOut()
            }
        }

        registerBook.setOnClickListener {
            RegisterBookActivity.startActivity(this)
            //finish()
        }
    }

    private fun showBooksList(booksRecovered: List<BookBackendResponse>) {
        if(!booksRecovered.isNullOrEmpty()) {
            var booksFrontImagesUrls: MutableList<String> = mutableListOf()
            for (book in booksRecovered) {
                if(!book.frontImage.isNullOrEmpty()) {
                    booksFrontImagesUrls.add(book.frontImage)
                }
            }
            galleryBooks.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            galleryBooks.adapter = BookFrontImageListAdapter(booksFrontImagesUrls)
        }
    }

    /*private fun showUsername(email: String) {
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list.adapter = ListAdapter(listOf(email))
    }*/

    private fun handleAuthenticationState(state: AuthenticationState) {
        if (!state.isAuthenticated()) {
            LoginActivity.startActivity(this)
            finish()
        }
    }
}