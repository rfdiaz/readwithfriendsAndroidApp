package com.readwithfriends.model

import androidx.lifecycle.MutableLiveData
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.getApi
import com.readwithfriends.model.api.model.ErrorBackend
import com.readwithfriends.model.api.model.UserBackend
import com.readwithfriends.model.api.model.UserBackendRequest
import com.readwithfriends.model.api.model.UserBooksBackendResponse

object UsersRepository {

    var booksRecovered = MutableLiveData<UserBooksBackendResponse>();

    var usersRecovered = MutableLiveData<List<UserBackend>>();

    fun addBookToUser(bookId: Integer,callback: (successCode: String?,error: ErrorBackend?) -> Unit){
        getApi().addBookToUser(bookId).makeRequest()
            .onSuccess {
                callback("Todo bien",null)
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }

    fun getUserBooks (userEmail: String?,callback: (successCode: String?,error: ErrorBackend?) -> Unit){
        getApi().getUserBooks(userEmail).makeRequest()
            .onSuccess {
                UsersRepository.booksRecovered.postValue(it)
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }

    fun getUsers (userEmail: String?,callback: (successCode: String?,error: ErrorBackend?) -> Unit){
        getApi().getUsers(userEmail).makeRequest()
            .onSuccess {
                UsersRepository.usersRecovered.postValue(it)
            }
            .onFailure { errorBackend, _ ->
                callback(null,errorBackend)
            }
    }
}