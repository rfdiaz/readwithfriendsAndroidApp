package com.readwithfriends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transform
import com.readwithfriends.extensions.transformNotNull
import com.readwithfriends.model.AuthenticationRepository
import com.readwithfriends.model.BooksRepository
import com.readwithfriends.model.UsersRepository
import com.readwithfriends.viewmodel.state.BookOperationsState
import com.readwithfriends.viewmodel.state.UsersOperationsState

class UserDataViewModel : ViewModel() {

    var email: LiveData<String> = AuthenticationRepository.getAuth().transformNotNull {
        it.email
    }

    var booksOperationState = UsersRepository.booksRecovered.transform {
        BookOperationsState.LocatedBooks(it) as BookOperationsState
    }

    var getUsersOperationState = UsersRepository.usersRecovered.transform{
        UsersOperationsState.Located(it)
    }

    fun addBookToUser (bookId: Integer){
        UsersRepository.addBookToUser(bookId){ successCode, errorBackend ->
            if(successCode.isNullOrBlank()){
                booksOperationState.postValue(errorBackend?.let { BookOperationsState.Duplicated(it) })
            }else{
                booksOperationState.postValue(BookOperationsState.AddedBook(successCode))
            }
        }
    }

    fun getUserBooks(userEmail: String?){
        UsersRepository.getUserBooks(userEmail){ successCode, errorBackend ->
            //TODO: Notificar error
        }
    }

    fun getUsers(userEmail: String?){
        UsersRepository.getUsers(userEmail){ successCode, errorBackend ->
           //TODO: Notificar error
        }
    }
}