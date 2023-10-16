package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Admin(
	val id: Long,
	val adminNumber: String,
	val user: User
)

class AdminDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<AdminDao>(Admins)
	var adminNumber by Admins.adminNumber
	var user by UserDao referencedOn Admins.user
}

object Admins : LongIdTable("admins") {
	val adminNumber = varchar("admin_number", length = 255).uniqueIndex()
	val user = reference("user_id", Users)
}