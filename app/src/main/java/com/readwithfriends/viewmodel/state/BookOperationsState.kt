package com.readwithfriends.viewmodel.state

import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.ErrorBackend
import com.readwithfriends.model.api.model.UserBooksBackendResponse
import com.readwithfriends.model.dto.BookDto

sealed class BookOperationsState {
    object Searching : BookOperationsState()
    data class Located(val book: MutableList<BookDto?>) : BookOperationsState()
    data class LocatedBooks(val books: UserBooksBackendResponse): BookOperationsState()
    data class Duplicated(val book: ErrorBackend): BookOperationsState()
    data class AddedBook(val successCode: String): BookOperationsState()
    data class InsertedBook (val bookInserted: BookDto?) : BookOperationsState()
}