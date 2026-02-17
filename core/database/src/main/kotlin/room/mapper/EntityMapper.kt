package com.example.course.core.database.room.mapper

interface EntityMapper<Entity, Domain> {
    fun asEntity(domain: Domain): Entity
    fun asDomain(entity: Entity): Domain
}