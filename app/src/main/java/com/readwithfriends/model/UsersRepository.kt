package com.readwithfriends.model

import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.getApi
import com.readwithfriends.model.api.model.ErrorBackend

object UsersRepository {

    fun addBookToUser(bookId: Integer,callback: (successCode: String?,error: ErrorBackend?) -> Unit){
        getApi().addBookToUser(bookId).makeRequest()
            .onSuccess {
                callback("Todo bien",null)
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }
}