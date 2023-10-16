package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Manager(
	val id: Long,
	val managerNumber: String,
	val user: User
)

class ManagerDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<ManagerDao>(Managers)
	var managerNumber by Managers.managerNumber
	var user by UserDao referencedOn Managers.user
}

object Managers : LongIdTable("managers") {
	val managerNumber = varchar("manager_number", length = 255).uniqueIndex()
	val user = reference("user_id", Users)
}