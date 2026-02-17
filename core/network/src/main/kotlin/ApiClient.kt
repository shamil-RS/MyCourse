package com.example.course.network

import android.content.Context
import com.example.course.core.model.CoursesResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.path
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface KtorApiService {
    suspend fun getCourses(
        title: String?,
        price: Double?,
        rate: Double?,
        startDate: String?,
        hasLike: Boolean?,
        publishDate: String?
    ): CoursesResponse
}

class CoursesApiClient @Inject constructor(
    private val client: HttpClient,
    private val json: Json
) : KtorApiService {

    override suspend fun getCourses(
        title: String?,
        price: Double?,
        rate: Double?,
        startDate: String?,
        hasLike: Boolean?,
        publishDate: String?
    ): CoursesResponse {
        val responseText = client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "drive.usercontent.google.com"
                path("u", "0", "uc")
                parameters.append("id", "15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q")
                parameters.append("export", "download")
                title?.let { parameters.append("title", it) }
                price?.let { parameters.append("price", it.toString()) }
                rate?.let { parameters.append("rate", it.toString()) }
                startDate?.let { parameters.append("startDate", it) }
                hasLike?.let { parameters.append("hasLike", it.toString()) }
                publishDate?.let { parameters.append("publishDate", it) }
            }
        }.bodyAsText()

        return json.decodeFromString<CoursesResponse>(responseText)
    }
}

class CoursesApiClient1 @Inject constructor(
    @ApplicationContext private val context: Context, // Добавляем контекст
    private val json: Json
) : KtorApiService {

    override suspend fun getCourses(
        title: String?,
        price: Double?,
        rate: Double?,
        startDate: String?,
        hasLike: Boolean?,
        publishDate: String?
    ): CoursesResponse {
        // Читаем файл courses.json из assets
        val responseText = context.assets.open("courses.json")
            .bufferedReader()
            .use { it.readText() }

        // Парсим JSON в вашу модель данных
        return json.decodeFromString<CoursesResponse>(responseText)
    }
}
