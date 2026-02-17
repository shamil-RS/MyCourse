package com.example.course.core.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.course.core.database.room.course.dao.CourseDao
import com.example.course.core.database.room.course.dbo.CourseDbo
import com.example.course.core.database.room.user.dao.UsersDao
import com.example.course.core.database.room.user.dbo.UserDbo

@Database(entities = [CourseDbo::class, UserDbo::class], version = 2)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun usersDao(): UsersDao
}