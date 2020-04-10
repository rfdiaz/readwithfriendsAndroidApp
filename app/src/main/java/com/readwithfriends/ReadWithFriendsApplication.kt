package com.readwithfriends

import android.app.Application
import androidx.room.Room
import com.readwithfriends.model.api.ReadWithFriendApiBuilder
import com.readwithfriends.model.api.ReadWithFriendsApi
import com.readwithfriends.model.database.ReadWithFriendsDatabase

class ReadWithFriendsApplication : Application() {

    companion object {
        lateinit var API: ReadWithFriendsApi
        lateinit var DATABASE: ReadWithFriendsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        API = ReadWithFriendApiBuilder.build()
        DATABASE = Room.databaseBuilder(this, ReadWithFriendsDatabase::class.java, "ReadWithFriendsDatabase").build()
    }
}

fun getApi() = ReadWithFriendsApplication.API
fun getDatabase() = ReadWithFriendsApplication.DATABASE