package com.example.course.core.data.di

import com.example.course.core.data.repository.AppPreferencesRepository
import com.example.course.core.data.repository.AppPreferencesRepositoryImpl
import com.example.course.core.data.repository.MainRepository
import com.example.course.core.data.repository.RepositoryImpl
import com.example.course.core.data.repository.UsersRepository
import com.example.course.core.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        impl: RepositoryImpl
    ): MainRepository

    @Binds
    @Singleton
    abstract fun bindsAppPreferencesRepository(
        repository: AppPreferencesRepositoryImpl
    ): AppPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindsUsersRepository(
        repository: UsersRepositoryImpl
    ): UsersRepository
}