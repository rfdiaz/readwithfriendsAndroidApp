package com.readwithfriends.model.mapper

import com.readwithfriends.model.api.model.UserTokenBackend
import com.readwithfriends.model.database.Auth
import com.readwithfriends.model.dto.AuthDto

fun UserTokenBackend.toDto(email: String): AuthDto {
    return AuthDto(email, token)
}

fun AuthDto.toEntity(): Auth {
    return Auth(email, token)
}

fun Auth.toDto(): AuthDto {
    return AuthDto(username, token)
}