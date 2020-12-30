package com.readwithfriends.model.api

import androidx.annotation.IntegerRes
import com.readwithfriends.model.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface ReadWithFriendsApi {

    @POST("users")
    fun signUp(@Body data: UserBackend): Call<Unit>

    @POST("auth/login")
    fun login(@Body data: UserBackend): Call<UserTokenBackend>

    @POST( "books/book/image")
    fun findBookByIsbnImage(@Body data:BookBackendRequest): Call<BookBackendResponse>

    @POST("books")
    fun saveBook(@Body data:SaveBookBackendRequest) : Call<BookBackendResponse>

    @PUT("users/book/{bookId}")
    fun addBookToUser(@Path("bookId") bookId:Integer) : Call<BookBackendResponse>

    @GET("users/books/{userEmail}")
    fun getUserBooks(@Path("userEmail") userEmail:String?): Call<UserBooksBackendResponse>

    @GET("users")
    fun getUsers(@Query("email") userEmail: String?): Call<List<UserBackend>>

    @GET("books")
    fun getBooks(@Query("filter") filter: String): Call<List<BookBackendResponse>>


}