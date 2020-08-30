package com.readwithfriends.model.dto

data class BookDto (
    var authors : String?,
    var categories: String?,
    var id : Integer?,
    var title : String?,
    var publisher: String?,
    var bookSummary : String?,
    var frontImage : String?,
    var isbn : String?,
    var storedInDB : Boolean?
)