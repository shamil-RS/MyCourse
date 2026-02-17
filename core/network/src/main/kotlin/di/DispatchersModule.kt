package com.example.course.network.di

import com.example.course.core.network.CoroutinesDispatchers
import com.example.course.core.network.CoroutinesDispatchersImpl
import com.example.course.core.network.CourseAppDispatchers
import com.example.course.core.network.Dispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DispatchersModule {

  @Provides
  @Dispatcher(CourseAppDispatchers.IO)
  fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {
    @Binds
    @Singleton
    fun bindsCoroutinesDispatchers(
        dispatchers: CoroutinesDispatchersImpl
    ): CoroutinesDispatchers
}