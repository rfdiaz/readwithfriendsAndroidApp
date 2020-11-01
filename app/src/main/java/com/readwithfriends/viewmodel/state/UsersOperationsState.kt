package com.readwithfriends.viewmodel.state

import com.readwithfriends.model.api.model.UserBackend

sealed class UsersOperationsState {
    data class Located(val users: List<UserBackend>) : UsersOperationsState()
}