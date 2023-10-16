package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Group(
	val id: Long,
	val code: String,
	val description: String
)

class GroupDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<GroupDao>(Groups)
	var code by Groups.code
	var description by Groups.description
	var user by UserDao referencedOn Groups.user
}

object Groups : LongIdTable("groups") {
	val code = varchar("code", length = 255).uniqueIndex()
	val description = text("description_of_group")
	val user = reference("user_id", Users)
}