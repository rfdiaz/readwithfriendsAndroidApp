package com.readwithfriends.extensions

import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.dto.BookDto

fun BookBackendResponse.convertBookBackendResponseToBookDto () : BookDto {
    return BookDto(this.authors,this.categories,this.id,this.title,this.publisher,this.bookSummary,this.frontImage,this.isbn,this.storedInDB)
}