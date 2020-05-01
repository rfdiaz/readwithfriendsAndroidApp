package com.readwithfriends.viewmodel

import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transform
import com.readwithfriends.model.AuthenticationRepository
import com.readwithfriends.viewmodel.state.AuthenticationState

class AuthenticationViewModel : ViewModel() {

    var authenticationState = AuthenticationRepository.getAuth().transform {
        if (it != null) AuthenticationState.Authenticated
        else AuthenticationState.NotAuthenticated
    }

    fun signUp(email: String, password: String,nickName: String, name: String) {
        authenticationState.postValue(AuthenticationState.Loading)
        AuthenticationRepository.signUp(email, password,nickName,name) { success, errorCode ->
            if (!success) {
                postSignUpError(errorCode)
            }
        }
    }

    fun login(email: String, password: String) {
        authenticationState.postValue(AuthenticationState.Loading)
        AuthenticationRepository.login(email, password) { success, errorCode ->
            if (!success) {
                postSignUpError(errorCode)
            }
        }
    }

    private fun postSignUpError(errorCode: Int) {
        authenticationState.postValue(
            AuthenticationState.AuthenticatingError(
                when (errorCode) {
                    409 -> "The username already exists, try a different one"
                    else -> "Something went wrong, please try again later"
                }))
    }

    fun signOut() {
        AuthenticationRepository.signOut()
    }
}