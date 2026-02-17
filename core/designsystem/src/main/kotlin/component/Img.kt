package com.example.course.core.designsystem.component

import com.example.course.designsystem.R

data class Img(
    val image: Int = 0
) {
    companion object {
        val mock = listOf(
            Img(image = R.drawable.javac),
            Img(image = R.drawable.generalist),
            Img(image = R.drawable.python),
            Img(image = R.drawable.analys),
            Img(image = R.drawable.analitik),
        )
    }
}