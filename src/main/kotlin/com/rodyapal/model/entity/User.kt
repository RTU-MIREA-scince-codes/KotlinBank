package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class User(
	val firstName: String,
	val lastName: String,
	val email: String,
	val telephoneNumber: String,
	val password: String,
	val active: Boolean,
	val userGroups: List<Group>,
	val id: Long? = null,
)

class UserDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<UserDao>(Users)
	var firstName by Users.firstName
	var lastName by Users.lastName
	var email by Users.email
	var telephoneNumber by Users.telephoneNumber
	var password by Users.password
	var active by Users.active
	val groups by GroupDao referrersOn Groups.user

	fun toEntity() = User(
		id = id.value,
		firstName = firstName,
		lastName = lastName,
		email = email,
		telephoneNumber = telephoneNumber,
		password = password,
		active = active,
		userGroups = groups.map { it.toEntity() }
	)
}

object Users : LongIdTable("users") {
	val firstName = varchar("first_name", length = 255)
	val lastName = varchar("last_name", length = 255)
	val email = varchar("email", length = 255).uniqueIndex()
	val telephoneNumber = varchar("telephone_number", length = 255).uniqueIndex()
	val password = varchar("password", length = 255)
	val active = bool("active")
}