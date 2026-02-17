package com.example.course.core.domain

import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.model.User
import javax.inject.Inject

interface GetUserByEmailUseCase {
    suspend operator fun invoke(email: String): User?
}

class GetUserByEmailUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUserByEmailUseCase {
    override suspend fun invoke(email: String): User? =
        usersRepository.getUserByEmail(email = email)
}