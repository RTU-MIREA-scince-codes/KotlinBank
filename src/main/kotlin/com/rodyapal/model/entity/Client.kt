package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Client(
	val id: Long,
	val clientNumber: String,
	val passportNumber: String,
	val user: User,
	val manager: Manager,
	val bankAccounts: List<BankAccount>
)

class ClientDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<ClientDao>(Clients)
	var clientNumber by Clients.clientNumber
	var passportNumber by Clients.passportNumber
	var user by UserDao referencedOn Clients.user
	var manager by ManagerDao referencedOn Clients.manager
	val bankAccounts by BankAccountDao referrersOn BankAccounts.client

	fun toEntity() = Client(
		id = id.value,
		clientNumber = clientNumber,
		passportNumber = passportNumber,
		user = user.toEntity(),
		manager = manager.toEntity(),
		bankAccounts = bankAccounts.map { it.toEntity() }
	)
}

object Clients : LongIdTable("clients") {
	val clientNumber = varchar("client_number", length = 32).uniqueIndex()
	val passportNumber = varchar("passport_number", length = 10).uniqueIndex()
	val user = reference("user_id", Users)
	val manager = reference("manager_id", Managers)
}