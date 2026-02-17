package com.example.course.core.navigation

import androidx.navigation3.runtime.NavKey
import com.example.course.core.model.Course
import kotlinx.serialization.Serializable

@Serializable
data object SignIn : NavKey

@Serializable
data object SignUp : NavKey

@Serializable
data class CourseScreen(
    val course: Course,
    val img: Int,
) : NavKey