package com.readwithfriends.model.api.model


import androidx.annotation.Keep

@Keep
class ErrorBackend (
    var errorCode : Int,
    var errorMessage: String?
)

