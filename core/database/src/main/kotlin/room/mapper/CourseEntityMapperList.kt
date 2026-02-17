package com.example.course.core.database.room.mapper

import com.example.course.core.database.room.course.dbo.CourseDbo
import com.example.course.core.model.Course

object CourseEntityMapperList : EntityMapper<List<CourseDbo>, List<Course>> {
    override fun asEntity(domain: List<Course>): List<CourseDbo> {
        return domain.map {
            CourseDbo(
                id = it.id,
                title = it.title,
                text = it.text,
                price = it.price,
                rate = it.rate,
                startDate = it.startDate,
                publishDate = it.publishDate,
                hasLike = it.hasLike,
            )
        }
    }

    override fun asDomain(entity: List<CourseDbo>): List<Course> {
        return entity.map {
            Course(
                id = it.id,
                title = it.title,
                text = it.text,
                price = it.price,
                rate = it.rate,
                startDate = it.startDate,
                publishDate = it.publishDate,
                hasLike = it.hasLike,
            )
        }
    }
}

fun List<Course>.asEntity(): List<CourseDbo> {
    return CourseEntityMapperList.asEntity(this)
}

fun List<CourseDbo>?.asDomain(): List<Course> {
    return CourseEntityMapperList.asDomain(this.orEmpty())
}