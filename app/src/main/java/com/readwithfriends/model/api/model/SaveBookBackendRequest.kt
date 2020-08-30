package com.readwithfriends.model.api.model

data class SaveBookBackendRequest (
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
