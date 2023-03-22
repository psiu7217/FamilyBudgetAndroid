package com.example.myapp.ui.profile

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
        )