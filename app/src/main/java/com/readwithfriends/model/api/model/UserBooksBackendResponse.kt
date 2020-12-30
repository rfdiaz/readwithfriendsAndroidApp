package com.readwithfriends.model.api.model

import androidx.annotation.Keep

@Keep
class UserBooksBackendResponse (
    val _id : String,
    val userBooks: MutableList<BookBackendResponse?>
)