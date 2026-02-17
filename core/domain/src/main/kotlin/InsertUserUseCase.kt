package com.example.course.core.domain

import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.model.User
import javax.inject.Inject

interface InsertUserUseCase {
    suspend operator fun invoke(user: User): Int
}

class InsertUserUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : InsertUserUseCase {
    override suspend fun invoke(user: User): Int =
        usersRepository.insertUser(user = user)
}