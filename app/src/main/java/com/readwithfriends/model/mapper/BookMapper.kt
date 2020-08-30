package com.readwithfriends.model.mapper

import com.readwithfriends.model.api.model.BookBackendResponse
import com.readwithfriends.model.api.model.SaveBookBackendRequest
import com.readwithfriends.model.dto.BookDto

fun BookBackendResponse.toDto(bookResponse: BookBackendResponse): BookDto{
    return BookDto(bookResponse.authors,bookResponse.categories,bookResponse.id,bookResponse.title,bookResponse.publisher,bookResponse.bookSummary,bookResponse.frontImage,bookResponse.isbn.replace("-",""),bookResponse.storedInDB)
}

fun BookDto.toSaveBookBackendRequest(bookDto: BookDto):SaveBookBackendRequest{
    return SaveBookBackendRequest(bookDto.authors,bookDto.categories,bookDto.id,bookDto.title,bookDto.publisher,bookDto.bookSummary,bookDto.frontImage,bookDto.isbn,bookDto.storedInDB)
}