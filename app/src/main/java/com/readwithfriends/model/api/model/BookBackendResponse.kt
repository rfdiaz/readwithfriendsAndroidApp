package com.readwithfriends.model.api.model

import androidx.annotation.Keep

@Keep
class BookBackendResponse (
    val authors : Array<String>,
    val categories: Array<String>,
    val id : Integer,
    val title : String,
    val publisher: String,
    val bookSummary : String,
    val frontImage : String,
    val isbn : String,
    val storedInDB : Boolean
)
