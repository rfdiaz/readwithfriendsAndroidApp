package com.readwithfriends.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Auth(
    @PrimaryKey var username: String,
    @ColumnInfo var token: String
)