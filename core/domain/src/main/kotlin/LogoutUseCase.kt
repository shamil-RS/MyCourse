package com.example.course.core.domain

import com.example.course.core.data.repository.AppPreferencesRepository
import javax.inject.Inject

interface LogoutUseCase {
    suspend operator fun invoke()
}

class LogoutUseCaseImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) : LogoutUseCase {
    override suspend fun invoke() =
        appPreferencesRepository.setCurrentUserId(userId = 0, stayConnected = false)
}