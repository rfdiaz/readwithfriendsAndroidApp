package com.readwithfriends.viewmodel.state

import com.readwithfriends.model.api.model.BookBackendResponse

sealed class BookOperationsState {
    object Searching : BookOperationsState()
    data class Located(val book: BookBackendResponse) : BookOperationsState()

}