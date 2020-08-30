package com.readwithfriends.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.readwithfriends.model.database.Auth

@Dao
interface AuthDao {
    @Query("SELECT * FROM auth LIMIT 1")
    fun getAuth(): LiveData<Auth?>

    @Query("SELECT * FROM auth LIMIT 1")
    fun getAuth2(): Auth?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuth(auth: Auth)

    @Update
    fun updateAuth(auth: Auth)

    @Query("DELETE FROM auth")
    fun deleteAuth()
}