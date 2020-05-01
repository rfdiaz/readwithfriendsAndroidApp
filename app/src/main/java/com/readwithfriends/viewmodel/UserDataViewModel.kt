package com.readwithfriends.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.readwithfriends.extensions.transformNotNull
import com.readwithfriends.model.AuthenticationRepository

class UserDataViewModel : ViewModel() {

    var email: LiveData<String> = AuthenticationRepository.getAuth().transformNotNull {
        it.email
    }

}