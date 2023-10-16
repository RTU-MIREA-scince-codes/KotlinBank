package com.rodyapal.model.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class BankAccount(
	val id: Long,
	val accountNumber: String,
	val balance: Double,
	val active: Boolean,
	val client: Client,
	val debitCards: List<DebitCard>
)

class BankAccountDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<BankAccountDao>(BankAccounts)
	var accountNumber by BankAccounts.accountNumber
	var balance by BankAccounts.balance
	var active by BankAccounts.active
	var client by ClientDao referencedOn BankAccounts.client
	val debitCards by DebitCardDao referrersOn DebitCards.account

	fun addFundsToBankAccount(amount: Double) {
		balance += amount
	}

	fun withdrawFundsFromBankAccount(amount: Double): Boolean {
		if (balance < amount) {
			return false
		}
		balance -= amount
		return true
	}
}

object BankAccounts : LongIdTable("bank_accounts") {
	val accountNumber = varchar("account_number", length = 255).uniqueIndex()
	val balance = double("balance")
	val active = bool("active")
	val client = reference("client_id", Clients)
}