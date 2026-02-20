package com.example.course.core.data.repository

import com.example.course.core.database.room.course.dao.CourseDao
import com.example.course.core.database.room.mapper.asDomain
import com.example.course.core.database.room.mapper.asEntity
import com.example.course.core.model.Course
import com.example.course.core.model.CoursesResponse
import com.example.course.core.network.CourseAppDispatchers
import com.example.course.core.network.Dispatcher
import com.example.course.network.KtorApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface MainRepository {
    fun getCourses(
        title: String?,
        price: Double?,
        rate: Double?,
        startDate: String?,
        hasLike: Boolean?,
        publishDate: String?,
    ): Flow<CoursesResponse>

    fun getCourseById(id: Int): Flow<Course?>
    suspend fun setFavorite(courseId: Int)
    fun getFavoriteCourses(): Flow<List<Course>>
    fun getAllCoursesFlow(): Flow<List<Course>>
}

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: KtorApiService,
    private val courseDao: CourseDao,
    @Dispatcher(CourseAppDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) : MainRepository {
    override fun getCourses(
        title: String?,
        price: Double?,
        rate: Double?,
        startDate: String?,
        hasLike: Boolean?,
        publishDate: String?,
    ): Flow<CoursesResponse> = flow {
        val response = api.getCourses(
            title = title,
            price = price,
            rate = rate,
            startDate = startDate,
            hasLike = hasLike,
            publishDate = publishDate,
        )
        courseDao.insertAll(response.courses.asEntity())
        emit(response)
    }.flowOn(ioDispatcher)

    override fun getCourseById(id: Int): Flow<Course?> =
        courseDao.getCourseById(id)
            .map { it?.asDomain() }

    override suspend fun setFavorite(courseId: Int) = courseDao.updateFavorite(courseId)

    override fun getFavoriteCourses(): Flow<List<Course>> =
        courseDao.getFavoriteCourses()
            .map { list -> list.asDomain() }

    override fun getAllCoursesFlow(): Flow<List<Course>> =
        courseDao.getAllCoursesFlow()
            .map { it.asDomain() }
}

