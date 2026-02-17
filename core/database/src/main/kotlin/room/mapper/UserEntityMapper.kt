package com.example.course.core.database.room.mapper

import com.example.course.core.database.room.user.dbo.UserDbo
import com.example.course.core.model.User

object UserEntityMapper : EntityMapper<UserDbo, User> {
    override fun asEntity(domain: User): UserDbo {
        return UserDbo(
            id = domain.id,
            email = domain.email,
            password = domain.password,
        )
    }

    override fun asDomain(entity: UserDbo): User {
        return User(
            id = entity.id,
            email = entity.email,
            password = entity.password,
        )
    }
}

fun User.asEntity(): UserDbo {
    return UserEntityMapper.asEntity(this)
}

fun UserDbo?.asDomain(): User? {
    return this?.let { UserEntityMapper.asDomain(it) }
}