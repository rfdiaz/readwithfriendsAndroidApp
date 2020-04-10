package com.readwithfriends.model

import androidx.lifecycle.LiveData
import com.readwithfriends.extensions.DEFAULT_ERROR_CODE
import com.readwithfriends.extensions.dbTask
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.extensions.transform
import com.readwithfriends.getApi
import com.readwithfriends.getDatabase
import com.readwithfriends.model.api.model.UserBackend
import com.readwithfriends.model.dto.AuthDto
import com.readwithfriends.model.mapper.toDto
import com.readwithfriends.model.mapper.toEntity

object AuthenticationRepository {

    fun signUp(email: String, password: String, callback: (success: Boolean, errorCode: Int) -> Unit) {
        getApi().signUp(UserBackend(email, password)).makeRequest()
            .onSuccess {
                login(email, password, callback)
            }
            .onFailure { errorCode, _ -> callback(false, errorCode) }
    }

    fun login(email: String, password: String, callback: (success: Boolean, errorCode: Int) -> Unit) {
        getApi().login(UserBackend(email, password)).makeRequest()
            .onSuccess {
                val authDto = it?.toDto(email)
                if (authDto != null) {
                    saveAuth(authDto) {
                        callback(true, DEFAULT_ERROR_CODE)
                    }
                } else {
                    callback(false, DEFAULT_ERROR_CODE)
                }
            }
            .onFailure { errorCode, _ ->  callback(false, errorCode) }
    }

    fun getAuth(): LiveData<AuthDto?> {
        return getDatabase().authDao().getAuth().transform {
            it?.toDto()
        }
    }

    fun signOut() {
        dbTask<Unit>().task {
            getDatabase().authDao().deleteAuth()
        }.run()
    }

    private fun saveAuth(authDto: AuthDto, callback: (Unit) -> Unit) {
        dbTask<Unit>().task {
            getDatabase().authDao().insertAuth(authDto.toEntity())
        }.result(callback).run()
    }

}