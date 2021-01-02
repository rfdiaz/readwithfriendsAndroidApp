package com.readwithfriends.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readwithfriends.extensions.DEFAULT_ERROR_BACKEND
import com.readwithfriends.extensions.convertBookBackendResponseToBookDto
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.getApi
import com.readwithfriends.model.api.model.*
import com.readwithfriends.model.dto.BookDto
import com.readwithfriends.model.mapper.toSaveBookBackendRequest

object BooksRepository {

    var booksRecovered = MutableLiveData<MutableList<BookDto?>>();

    var bookInserted = MutableLiveData<BookDto?>()

    //Recibe el isbn en formato base64, es la imagen
    fun findBookByIsbn(isbnFormatBase64: String, callback: (error: ErrorBackend) -> Unit) {
        var bookRequest = BookBackendRequest(isbnFormatBase64)
        getApi().findBookByIsbnImage(bookRequest).makeRequest()
            .onSuccess {
                val booksList = mutableListOf<BookDto?>()
                booksList.add(it?.convertBookBackendResponseToBookDto())
                booksRecovered.postValue(booksList)
            }
            .onFailure { errorBackend, _ ->
                callback(errorBackend)
            }
    }

    //Recibe el isbn en formato base64, es la imagen
    fun findBooks(filter: String, callback: (error: ErrorBackend) -> Unit) {
        getApi().getBooks(filter).makeRequest()
            .onSuccess {
                val booksList = mutableListOf<BookDto?>()
                it?.forEach { it->booksList.add(it.convertBookBackendResponseToBookDto())}
                booksRecovered.postValue(booksList)
            }
            .onFailure { errorBackend, _ ->
                callback(errorBackend)
            }
    }

    fun saveBook (book: SaveBookBackendRequest,callback: (successCode: BookDto?,error: ErrorBackend?) -> Unit){
        getApi().saveBook(book).makeRequest()
            .onSuccess {
                bookInserted.postValue(it?.convertBookBackendResponseToBookDto())
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }

}