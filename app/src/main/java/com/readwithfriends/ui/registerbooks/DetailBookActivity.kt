package com.readwithfriends.ui.registerbooks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.readwithfriends.R
import com.readwithfriends.extensions.getViewModel
import com.readwithfriends.extensions.invisible
import com.readwithfriends.extensions.observe
import com.readwithfriends.extensions.visible
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.dto.BookDto
import com.readwithfriends.model.mapper.toDto
import com.readwithfriends.ui.home.HomeActivity
import com.readwithfriends.viewmodel.BookDataViewModel
import com.readwithfriends.viewmodel.UserDataViewModel
import com.readwithfriends.viewmodel.state.BookOperationsState
import kotlinx.android.synthetic.main.activity_detailbook.*
import kotlinx.coroutines.Deferred

class DetailBookActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, DetailBookActivity::class.java))
        }
        var bookRecovered  : BookDto? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailbook)

        val activity = this@DetailBookActivity

        bookTitle.setText(bookRecovered?.title)
        bookAuthor.setText(bookRecovered?.authors)
        bookIsbn.setText(bookRecovered?.isbn)
        if(bookRecovered?.storedInDB!!){
            haveBook.visible()
        }else{
            saveBook.visible()
        }


        getViewModel(UserDataViewModel::class.java) {
            booksOperationState.observe(activity) {
                if (it is BookOperationsState.Duplicated) {
                    Toast.makeText(activity, it.book.errorMessage, Toast.LENGTH_SHORT).show()
                } else if (it is BookOperationsState.AddedBook) {
                    val intent = Intent(this@DetailBookActivity, BooksListActivity::class.java)
                    startActivity(intent)
                    //finish()
                }
            }
            //Cuando quiero indicar que tengo el libro que acabo de buscar
            haveBook.setOnClickListener() {
                this.addBookToUser(bookRecovered?.id!!)
            }
        }

        getViewModel(BookDataViewModel::class.java) {
            bookInsertedOperation.observe(activity){
                if(it is BookOperationsState.InsertedBook){
                    bookRecovered?.id = it.bookInserted?.id
                }
            }
            //Cuando quiero registrar un libro en la base de datos
            saveBook.setOnClickListener() {
                var resultSaveBook: String = this.saveBook(bookRecovered)
                haveBook.visible()
                saveBook.invisible()

                Toast.makeText(activity, resultSaveBook, Toast.LENGTH_SHORT).show()
            }
        }

    }

}

