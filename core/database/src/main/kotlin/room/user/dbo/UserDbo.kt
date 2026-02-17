package com.example.course.core.database.room.user.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val password: String,
)
