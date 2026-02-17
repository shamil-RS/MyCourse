package com.example.course.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.course.core.database.room.CourseDatabase
import com.example.course.core.database.room.course.dao.CourseDao
import com.example.course.core.database.room.user.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): CourseDatabase = Room
        .databaseBuilder(context, CourseDatabase::class.java, "app_database")
        .fallbackToDestructiveMigration(false).build()

    @Provides
    @Singleton
    fun providesCourseDao(
        database: CourseDatabase
    ): CourseDao = database.courseDao()

    @Provides
    @Singleton
    fun providesUsersDao(
        database: CourseDatabase
    ): UsersDao = database.usersDao()
}