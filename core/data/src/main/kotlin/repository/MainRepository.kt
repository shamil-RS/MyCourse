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
import kotlinx.coroutines.flow.first
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
    val ioDispatcher: CoroutineDispatcher
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

    override fun getCourseById(id: Int): Flow<Course?> {
        return courseDao.getCourseById(id)
            .map { it?.asDomain() }
            .flowOn(ioDispatcher)
    }

    override suspend fun setFavorite(courseId: Int) {
        val course = courseDao.getCourseById(courseId).first()

        course?.let {
            courseDao.updateFavorite(it.id, !it.hasLike)
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return courseDao.getFavoriteCourses()
            .map { list -> list.asDomain() }
            .flowOn(ioDispatcher)
    }

    override fun getAllCoursesFlow(): Flow<List<Course>> {
        return courseDao.getAllCoursesFlow()
            .map { it.asDomain() }
            .flowOn(ioDispatcher)
    }
}

