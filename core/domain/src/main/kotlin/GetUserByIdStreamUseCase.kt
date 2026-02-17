package com.example.course.core.domain

import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetUserByIdStreamUseCase {
    operator fun invoke(id: Int): Flow<User?>
}

class GetUserByIdStreamUseCaseImpl @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUserByIdStreamUseCase {
    override fun invoke(id: Int): Flow<User?> =
        usersRepository.getUserByIdStream(id = id)
}