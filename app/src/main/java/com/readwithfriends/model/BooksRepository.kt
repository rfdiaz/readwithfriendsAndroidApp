package com.readwithfriends.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readwithfriends.extensions.DEFAULT_ERROR_BACKEND
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.getApi
import com.readwithfriends.model.api.model.*
import com.readwithfriends.model.dto.BookDto
import com.readwithfriends.model.mapper.toSaveBookBackendRequest

object BooksRepository {

    var bookRecovered = MutableLiveData<BookBackendResponse>();

    //Recibe el isbn en formato base64, es la imagen
    fun findBookByIsbn(isbnFormatBase64: String, callback: (error: ErrorBackend) -> Unit) {
        var bookRequest = BookBackendRequest(isbnFormatBase64)
        getApi().findBookByIsbnImage(bookRequest).makeRequest()
            .onSuccess {
                bookRecovered.postValue(it)
            }
            .onFailure { errorBackend, _ ->
                callback(errorBackend)
            }
    }

    fun saveBook (book: SaveBookBackendRequest,callback: (successCode: String?,error: ErrorBackend?) -> Unit){
        getApi().saveBook(book).makeRequest()
            .onSuccess {
                bookRecovered.postValue(it)
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }


}