package com.readwithfriends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transformNotNull
import com.readwithfriends.model.AuthenticationRepository
import com.readwithfriends.model.UsersRepository

class UserDataViewModel : ViewModel() {

    var email: LiveData<String> = AuthenticationRepository.getAuth().transformNotNull {
        it.email
    }

    fun addBookToUser (bookId: Integer): String {
        var exitCode : String = ""
        UsersRepository.addBookToUser(bookId){ successCode, errorBackend ->
            if(successCode.isNullOrBlank()){
                exitCode = "error"
            }else{
                exitCode = "exito"
            }
        }
        return exitCode
    }

}