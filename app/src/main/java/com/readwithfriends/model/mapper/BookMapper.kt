package com.readwithfriends.model.mapper

import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.SaveBookBackendRequest
import com.readwithfriends.model.dto.BookDto

fun BookBackendResponse.toDto(): BookDto{
    return BookDto(this.authors,this.categories,this.id,this.title,this.publisher,this.bookSummary,this.frontImage,this.isbn.replace("-",""),this.storedInDB)
}

fun BookDto.toSaveBookBackendRequest():SaveBookBackendRequest{
    return SaveBookBackendRequest(this.authors,this.categories,this.id,this.title,this.publisher,this.bookSummary,this.frontImage,this.isbn,this.storedInDB)
}