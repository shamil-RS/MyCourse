package com.example.course.core.domain

import com.example.course.core.data.repository.AppPreferencesRepository
import javax.inject.Inject

interface SetCurrentUserIdUseCase {
    suspend operator fun invoke(userId: Int, stayConnected: Boolean)
}

class SetCurrentUserIdUseCaseImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : SetCurrentUserIdUseCase {
    override suspend fun invoke(userId: Int, stayConnected: Boolean) =
        appPreferencesRepository.setCurrentUserId(userId = userId, stayConnected = stayConnected)
}