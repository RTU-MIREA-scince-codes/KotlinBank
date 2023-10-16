package com.rodyapal.model.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class DebitCard(
	val id: Long,
	val cardNumber: String,
	val cvv: String,
	val expirationDate: LocalDate,
	val balance: Double,
	val active: Boolean
)

class DebitCardDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<DebitCardDao>(DebitCards)
	var cardNumber by DebitCards.cardNumber
	var cvv by DebitCards.cvv
	var expirationDate by DebitCards.expirationDate
	var balance by DebitCards.balance
	var active by DebitCards.active
	var bankAccount by BankAccountDao referencedOn DebitCards.account
}

object DebitCards : LongIdTable() {
	val cardNumber = varchar("cardNumber", length = 16).uniqueIndex()
	val cvv = varchar("cvv", length = 3)
	val expirationDate = date("expirationDate")
	val balance = double("balance")
	val active = bool("active")
	val account = reference("bank_account_id", BankAccounts)
}