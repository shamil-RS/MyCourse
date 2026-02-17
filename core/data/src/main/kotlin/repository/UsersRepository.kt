package com.example.course.core.data.repository

import com.example.course.core.data.LocalAccountPreferences
import com.example.course.core.database.room.mapper.asDomain
import com.example.course.core.database.room.mapper.asEntity
import com.example.course.core.database.room.user.dao.UsersDao
import com.example.course.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UsersRepository {
    fun getUserByIdStream(id: Int): Flow<User?>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun isEmailAvailable(email: String): Boolean
    suspend fun insertUser(user: User): Int
    suspend fun updateUser(user: User)
    suspend fun getCurrentUser(): User?
}

class UsersRepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val localAccountPreferences: LocalAccountPreferences
) : UsersRepository {

    override fun getUserByIdStream(id: Int): Flow<User?> =
        usersDao.getUserByIdStream(id = id).map { userEntity ->
            userEntity?.asDomain()
        }

    override suspend fun getUserById(id: Int): User? {
        val userEntity = usersDao.getUserById(id = id)
        return userEntity?.asDomain()
    }

    override suspend fun getUserByEmail(email: String): User? {
        val userEntity = usersDao.getUserByEmail(email = email)
        return userEntity?.asDomain()
    }

    override suspend fun isEmailAvailable(email: String): Boolean =
        !usersDao.isEmailInUse(email = email)

    override suspend fun insertUser(user: User): Int {
        val userEntity = user.asEntity()
        return usersDao.insertUserOrIgnore(userDbo = userEntity).toInt()
    }

    override suspend fun updateUser(user: User) {
        val userEntity = user.asEntity()
        usersDao.updateUser(userDbo = userEntity)
    }

    override suspend fun getCurrentUser(): User? {
        val currentUserId = localAccountPreferences.getCurrentUserId()
        return if (currentUserId != 0) getUserById(currentUserId)
        else null
    }
}