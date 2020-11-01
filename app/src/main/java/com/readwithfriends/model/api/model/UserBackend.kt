package com.readwithfriends.model.api.model

import androidx.annotation.Keep

@Keep
class UserBackend (
    var email: String,
    var password: String,
    var nickName: String?,
    var name: String?,
    var picture: String?
)