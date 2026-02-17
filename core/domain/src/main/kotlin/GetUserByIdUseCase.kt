package com.example.course.core.domain

import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.model.User
import javax.inject.Inject

interface GetUserByIdUseCase {
    suspend operator fun invoke(id: Int): User?
}

class GetUserByIdUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUserByIdUseCase {
    override suspend fun invoke(id: Int): User? =
        usersRepository.getUserById(id = id)
}