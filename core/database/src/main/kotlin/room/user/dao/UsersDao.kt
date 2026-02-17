package com.example.course.core.database.room.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.course.core.database.room.user.dbo.UserDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Query(value = "SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserByIdStream(id: Int): Flow<UserDbo?>

    @Query(value = "SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserDbo?

    @Query(value = "SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserDbo?

    @Query(value = "SELECT COUNT() FROM users WHERE email = :email LIMIT 1")
    suspend fun isEmailInUse(email: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserOrIgnore(userDbo: UserDbo): Long

    @Update
    suspend fun updateUser(userDbo: UserDbo)

}