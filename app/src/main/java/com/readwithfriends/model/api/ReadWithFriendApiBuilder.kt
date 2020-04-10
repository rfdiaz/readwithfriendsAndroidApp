package com.readwithfriends.model.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ReadWithFriendApiBuilder {

    const val BASE_URL = "http://readwithfriendsfodemos.herokuapp.com"

    fun build(): ReadWithFriendsApi {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headersInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build().create(ReadWithFriendsApi::class.java)
    }

    private val headersInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer " + "TODO")
        chain.proceed(request.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}