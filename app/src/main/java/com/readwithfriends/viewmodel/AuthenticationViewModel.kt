package com.readwithfriends.viewmodel

import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transform
import com.readwithfriends.model.AuthenticationRepository
import com.readwithfriends.model.api.model.ErrorBackend
import com.readwithfriends.viewmodel.state.AuthenticationState

class AuthenticationViewModel : ViewModel() {

    var authenticationState = AuthenticationRepository.getAuth().transform {
        if (it != null) AuthenticationState.Authenticated
        else AuthenticationState.NotAuthenticated
    }

    fun signUp(email: String, password: String,nickName: String, name: String) {
        authenticationState.postValue(AuthenticationState.Loading)
        AuthenticationRepository.signUp(email, password,nickName,name) { success, errorBackend ->
            if (!success) {
                postSignUpError(errorBackend)
            }
        }
    }

    fun login(email: String, password: String) {
        authenticationState.postValue(AuthenticationState.Loading)
        AuthenticationRepository.login(email, password) { success, errorBackend ->
            if (!success) {
                postSignUpError(errorBackend)
            }
        }
    }

    private fun postSignUpError(errorBackend: ErrorBackend) {
        authenticationState.postValue(AuthenticationState.AuthenticatingError(errorBackend.errorMessage))
    }

    fun signOut() {
        AuthenticationRepository.signOut()
    }
}