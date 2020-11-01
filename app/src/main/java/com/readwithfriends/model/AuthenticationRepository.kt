package com.readwithfriends.model

import androidx.lifecycle.LiveData
import com.readwithfriends.extensions.DEFAULT_ERROR_BACKEND
import com.readwithfriends.extensions.dbTask
import com.readwithfriends.extensions.makeRequest
import com.readwithfriends.extensions.transform
import com.readwithfriends.getApi
import com.readwithfriends.getDatabase
import com.readwithfriends.model.api.model.ErrorBackend
import com.readwithfriends.model.api.model.UserBackend
import com.readwithfriends.model.dto.AuthDto
import com.readwithfriends.model.mapper.toDto
import com.readwithfriends.model.mapper.toEntity

object AuthenticationRepository {

    fun signUp(email: String, password: String,nickName:String,name: String,picture :String, callback: (success: Boolean, errorBackend: ErrorBackend) -> Unit) {
        getApi().signUp(UserBackend(email, password,nickName,name,picture)).makeRequest()
            .onSuccess {
                login(email, password, callback)
            }
            .onFailure { errorBackend, _ -> callback(false, errorBackend) }
    }

    fun login(email: String, password: String, callback: (success: Boolean, errorBackend: ErrorBackend) -> Unit) {
        getApi().login(UserBackend(email, password,null,null,null)).makeRequest()
            .onSuccess {
                val authDto = it?.toDto(email)
                if (authDto != null) {
                    saveAuth(authDto) {
                        callback(true, DEFAULT_ERROR_BACKEND)
                    }
                } else {
                    callback(false, DEFAULT_ERROR_BACKEND)
                }
            }
            .onFailure { errorCode, _ ->  callback(false, errorCode) }
    }

    fun getAuth(): LiveData<AuthDto?> {
            return getDatabase().authDao().getAuth().transform {
                it?.toDto()
            }
    }

    //Este metodo se usa cuando quieres recuperar la informacion de la room
    //directamente sin usar observables.
    fun getAuthToken(): String? {
        return getDatabase().authDao().getAuth2()?.token.toString();
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