package com.readwithfriends.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.readwithfriends.extensions.DEFAULT_ERROR_BACKEND
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.getApi
import com.readwithfriends.model.api.model.BookBackendRequest
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.ErrorBackend

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

}