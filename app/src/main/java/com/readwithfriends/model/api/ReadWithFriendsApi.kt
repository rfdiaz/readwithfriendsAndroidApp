package com.readwithfriends.model.api

import com.readwithfriends.model.api.model.BookBackendRequest
import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.UserBackend
import com.readwithfriends.model.api.model.UserTokenBackend
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReadWithFriendsApi {

    @POST("users")
    fun signUp(@Body data: UserBackend): Call<Unit>

    @POST("auth/login")
    fun login(@Body data: UserBackend): Call<UserTokenBackend>

    @POST( "books/book/image")
    fun findBookByIsbnImage(@Body data:BookBackendRequest): Call<BookBackendResponse>

}