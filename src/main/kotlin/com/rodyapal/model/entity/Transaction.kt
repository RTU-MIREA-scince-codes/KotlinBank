package com.rodyapal.model.entity

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class Transaction(
	val id: Long,
	val transactionNumber: String,
	val transactionAmount: Double,
	val transactionDate: LocalDate,
	val client: Client,
	val manager: Manager,
	val bankAccountFrom: BankAccount,
	val bankAccountTo: BankAccount
)

class TransactionDao(id: EntityID<Long>) : LongEntity(id) {
	companion object : LongEntityClass<TransactionDao>(Transactions)
	var transactionNumber by Transactions.transactionNumber
	var transactionAmount by Transactions.transactionAmount
	var transactionDate by Transactions.transactionDate
	var client by ClientDao referencedOn Transactions.client
	var manager by ManagerDao referencedOn Transactions.manager
	var bankAccountFrom by BankAccountDao referencedOn Transactions.bankAccountFrom
	var bankAccountTo by BankAccountDao referencedOn Transactions.bankAccountTo
}

object Transactions : LongIdTable("transactions") {
	val transactionNumber = varchar("transaction_number", length = 255).uniqueIndex()
	val transactionAmount = double("amount")
	val transactionDate = date("transaction_date")
	val client = reference("client_id", Clients)
	val manager = reference("manager_id", Clients)
	val bankAccountFrom = reference("bank_account_id", BankAccounts)
	val bankAccountTo = reference("bank_account_id_to", BankAccounts)
}