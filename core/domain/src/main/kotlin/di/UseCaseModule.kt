package com.example.course.core.domain.di

import com.example.course.core.domain.GetCurrentUserIdUseCase
import com.example.course.core.domain.GetCurrentUserIdUseCaseImpl
import com.example.course.core.domain.GetUserByEmailUseCase
import com.example.course.core.domain.GetUserByEmailUseCaseImpl
import com.example.course.core.domain.GetUserByIdStreamUseCase
import com.example.course.core.domain.GetUserByIdStreamUseCaseImpl
import com.example.course.core.domain.GetUserByIdUseCase
import com.example.course.core.domain.GetUserByIdUseCaseImpl
import com.example.course.core.domain.InsertUserUseCase
import com.example.course.core.domain.InsertUserUseCaseImpl
import com.example.course.core.domain.IsEmailAvailableUseCase
import com.example.course.core.domain.IsEmailAvailableUseCaseImpl
import com.example.course.core.domain.IsStayConnectedEnabledUseCase
import com.example.course.core.domain.IsStayConnectedEnabledUseCaseImpl
import com.example.course.core.domain.LogoutUseCase
import com.example.course.core.domain.LogoutUseCaseImpl
import com.example.course.core.domain.SetCurrentUserIdUseCase
import com.example.course.core.domain.SetCurrentUserIdUseCaseImpl
import com.example.course.core.domain.UpdateUserUseCase
import com.example.course.core.domain.UpdateUserUseCaseImpl
import com.example.course.core.domain.ValidateEmailFieldUseCase
import com.example.course.core.domain.ValidateEmailFieldUseCaseImpl
import com.example.course.core.domain.ValidateSimpleFieldUseCase
import com.example.course.core.domain.ValidateSimpleFieldUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    @ViewModelScoped
    fun bindsIsStayConnectedEnabledUseCase(
        useCase: IsStayConnectedEnabledUseCaseImpl
    ): IsStayConnectedEnabledUseCase

    @Binds
    @ViewModelScoped
    fun bindsSetCurrentUserIdUseCase(
        useCase: SetCurrentUserIdUseCaseImpl
    ): SetCurrentUserIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetCurrentUserIdUseCase(
        useCase: GetCurrentUserIdUseCaseImpl
    ): GetCurrentUserIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsLogoutUseCase(
        useCase: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByIdStreamUseCase(
        useCase: GetUserByIdStreamUseCaseImpl
    ): GetUserByIdStreamUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByIdUseCase(
        useCase: GetUserByIdUseCaseImpl
    ): GetUserByIdUseCase

    @Binds
    @ViewModelScoped
    fun bindsGetUserByEmailUseCase(
        useCase: GetUserByEmailUseCaseImpl
    ): GetUserByEmailUseCase

    @Binds
    @ViewModelScoped
    fun bindsIsEmailAvailableUseCase(
        useCase: IsEmailAvailableUseCaseImpl
    ): IsEmailAvailableUseCase

    @Binds
    @ViewModelScoped
    fun bindsInsertUserUseCase(
        useCase: InsertUserUseCaseImpl
    ): InsertUserUseCase

    @Binds
    @ViewModelScoped
    fun bindsUpdateUserUseCase(
        useCase: UpdateUserUseCaseImpl
    ): UpdateUserUseCase

    @Binds
    @ViewModelScoped
    fun bindsValidateSimpleFieldUseCase(
        useCase: ValidateSimpleFieldUseCaseImpl
    ): ValidateSimpleFieldUseCase

    @Binds
    @ViewModelScoped
    fun bindsValidateEmailFieldUseCase(
        useCase: ValidateEmailFieldUseCaseImpl
    ): ValidateEmailFieldUseCase
}