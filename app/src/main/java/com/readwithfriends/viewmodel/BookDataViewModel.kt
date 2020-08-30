package com.readwithfriends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transform
import com.readwithfriends.model.AuthenticationRepository
import com.readwithfriends.model.BooksRepository
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.dto.BookDto
import com.readwithfriends.model.mapper.toSaveBookBackendRequest
import com.readwithfriends.viewmodel.state.AuthenticationState
import com.readwithfriends.viewmodel.state.BookOperationsState

class BookDataViewModel : ViewModel() {

    var bookOperationState = BooksRepository.bookRecovered.transform {
        BookOperationsState.Located(it) as BookOperationsState
    }

    fun findBookByIsbn(isbnFormatBase64: String) {
        bookOperationState.postValue(BookOperationsState.Searching)
        BooksRepository.findBookByIsbn(isbnFormatBase64) { errorBackend ->
            //bookOperationState.postValue(BookOperationsState.Located)
            //TODO: Notificar error
        }
    }

    fun saveBook(bookDto: BookDto): String{
        var exitCode : String = ""
        BooksRepository.saveBook(bookDto.toSaveBookBackendRequest(bookDto)){successCode,errorBackend ->
            if(successCode.isNullOrBlank()){
               exitCode = "error"
            }else{
                exitCode = "exito"
            }
        }
        return exitCode
    }


}