package com.example.course.core.domain

import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.model.User
import javax.inject.Inject

interface UpdateUserUseCase {
    suspend operator fun invoke(user: User)
}

class UpdateUserUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : UpdateUserUseCase {
    override suspend fun invoke(user: User) =
        usersRepository.updateUser(user = user)
}