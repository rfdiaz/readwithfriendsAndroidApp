package com.readwithfriends.viewmodel.state

sealed class AuthenticationState {
    object Authenticated: AuthenticationState()
    object NotAuthenticated: AuthenticationState()
    data class AuthenticatingError(val message: String): AuthenticationState()
    object Loading: AuthenticationState()

    fun isAuthenticated(): Boolean {
        return this is Authenticated
    }
}