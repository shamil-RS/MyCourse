package com.example.course.core.database.room.mapper

import com.example.course.core.database.room.course.dbo.CourseDbo
import com.example.course.core.model.Course

object CourseEntityMapper : EntityMapper<CourseDbo, Course> {
    override fun asEntity(domain: Course): CourseDbo {
        return CourseDbo(
            id = domain.id,
            title = domain.title,
            text = domain.text,
            price = domain.price,
            rate = domain.rate,
            startDate = domain.startDate,
            publishDate = domain.publishDate,
            hasLike = domain.hasLike,
        )
    }

    override fun asDomain(entity: CourseDbo): Course {
        return Course(
            id = entity.id,
            title = entity.title,
            text = entity.text,
            price = entity.price,
            rate = entity.rate,
            startDate = entity.startDate,
            publishDate = entity.publishDate,
            hasLike = entity.hasLike,
        )
    }
}

fun Course.asEntity(): CourseDbo {
    return CourseEntityMapper.asEntity(this)
}

fun CourseDbo?.asDomain(): Course? {
    return this?.let { CourseEntityMapper.asDomain(it) }
}