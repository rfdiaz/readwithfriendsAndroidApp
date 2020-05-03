package com.readwithfriends.extensions

import com.readwithfriends.model.api.model.ErrorBackend
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val DEFAULT_ERROR_BACKEND = ErrorBackend(0,errorMessage = "Error por defecto en la comunicacion con el servidor")

class RequestCallback<T>(private var handler: ((T?) -> Unit)? = null, private var errHandler: ((ErrorBackend, ResponseBody?) -> Unit)? = null) {
    fun onFailure(handler: (ErrorBackend, ResponseBody?) -> Unit): RequestCallback<T> {
        this.errHandler = handler
        return this
    }

    fun onSuccess(handler: (T?) -> Unit): RequestCallback<T> {
        this.handler = handler
        return this
    }

    fun resolve(result: T?) = handler?.invoke(result)
    fun reject(errorBackend: ErrorBackend, errorBody: ResponseBody? = null) = errHandler?.invoke(errorBackend, errorBody)
}

fun <T> Call<T>.makeRequest(): RequestCallback<T> {
    val requestCallback = RequestCallback<T>()
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            if (response?.isSuccessful != false) {
                requestCallback.resolve(response?.body())
            } else {
                var jsonError :JSONObject = JSONObject(response.errorBody()?.string())
                var errorBackend = ErrorBackend(response.code(), jsonError.getString("error"))
                requestCallback.reject(errorBackend, response.errorBody())
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            requestCallback.reject(DEFAULT_ERROR_BACKEND)
        }
    })
    return requestCallback
}