package com.example.myapp.ui.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): User

    @Query("DELETE FROM users WHERE id = :userId")
    fun deleteUserById(userId: Int)

    // ... add additional queries as needed
}