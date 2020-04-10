package com.readwithfriends.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.readwithfriends.model.database.dao.AuthDao

@Database(entities = arrayOf(Auth::class), version = 1)
abstract class ReadWithFriendsDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
}