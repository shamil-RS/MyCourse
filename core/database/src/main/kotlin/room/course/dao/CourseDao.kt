package com.example.course.core.database.room.course.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.course.core.database.room.course.dbo.CourseDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CourseDbo>)

    @Query("SELECT * FROM course")
    fun getAllCoursesFlow(): Flow<List<CourseDbo>>

    @Query("SELECT * FROM course WHERE id = :id_")
    fun getCourseById(id_: Int): Flow<CourseDbo?>

    @Query("UPDATE course SET hasLike = NOT hasLike WHERE id = :id")
    suspend fun updateFavorite(id: Int)

    @Query("SELECT * FROM course WHERE hasLike = 1")
    fun getFavoriteCourses(): Flow<List<CourseDbo>>
}