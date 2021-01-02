package com.readwithfriends.ui.registerbooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.readwithfriends.R
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.observe
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.UserBooksBackendResponse
import com.readwithfriends.ui.home.HomeActivity
import com.readwithfriends.viewmodel.BookDataViewModel
import com.readwithfriends.viewmodel.UserDataViewModel
import com.readwithfriends.viewmodel.state.BookOperationsState
import kotlinx.android.synthetic.main.activity_user_books.*

class BooksListActivity : AppCompatActivity(){

    private lateinit var booksRecovered : UserBooksBackendResponse

    companion object {
        //Esta variable se cubrira en otra activity e indica los libros a recuperar y pintar
        var booksIds: List<Integer>? = null
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, BooksListActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_books)
        val activity = this@BooksListActivity

        getViewModel(UserDataViewModel::class.java) {
            email.observe(activity) {
                if(booksIds.isNullOrEmpty()){
                    this.getUserBooks(it)
                }else{
                    //TODO: Implementar el camino de cuando no se estan recuperando los libros del usuario
                }
            }

            booksOperationState.observe(activity){
                if(it is BookOperationsState.LocatedBooks){
                    booksRecovered = it.books
                    showBooksList(booksRecovered.userBooks)
                }
            }
        }
    }

    private fun showBooksList(booksRecovered: MutableList<BookBackendResponse?>) {
        books.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
       // books.adapter = BooksListAdapter(booksRecovered)
    }

}