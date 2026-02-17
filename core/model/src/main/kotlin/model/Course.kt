package com.example.course.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val price: String = "",
    val rate: Double = 0.0,
    val hasLike: Boolean = false,
    val startDate: String = "",
    val publishDate: String = "",
)

@Serializable
data class CoursesResponse(
    val courses: List<Course>
)