package com.readwithfriends.extensions

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val DEFAULT_ERROR_CODE = 0

class RequestCallback<T>(private var handler: ((T?) -> Unit)? = null, private var errHandler: ((Int, ResponseBody?) -> Unit)? = null) {
    fun onFailure(handler: (Int, ResponseBody?) -> Unit): RequestCallback<T> {
        this.errHandler = handler
        return this
    }

    fun onSuccess(handler: (T?) -> Unit): RequestCallback<T> {
        this.handler = handler
        return this
    }

    fun resolve(result: T?) = handler?.invoke(result)
    fun reject(errorCode: Int, errorBody: ResponseBody? = null) = errHandler?.invoke(errorCode, errorBody)
}

fun <T> Call<T>.makeRequest(): RequestCallback<T> {
    val requestCallback = RequestCallback<T>()
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.isSuccessful != false) {
                requestCallback.resolve(response?.body())
            } else {
                requestCallback.reject(response.code(), response.errorBody())
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            requestCallback.reject(DEFAULT_ERROR_CODE)
        }
    })
    return requestCallback
}