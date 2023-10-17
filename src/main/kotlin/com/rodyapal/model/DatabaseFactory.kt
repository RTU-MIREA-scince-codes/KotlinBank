package com.rodyapal.model

import com.rodyapal.model.entity.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

private const val DATABASE = "bank_database"
private const val HOST = "database"
private const val PORT = "5432"
private const val URL = "jdbc:postgresql://$HOST:$PORT/$DATABASE"
private const val USERNAME = "service"
private const val PASSWORD = "12345654321"
private const val DRIVER = "org.postgresql.Driver"

val databaseInstance = Database.connect(
	url = URL,
	driver = DRIVER,
	user = USERNAME,
	password = PASSWORD
).also { db ->
	transaction(db) {
		SchemaUtils.create(
			Groups, Users, Admins, Managers, Clients, BankAccounts, DebitCards, Transactions
		)
	}
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
	newSuspendedTransaction(Dispatchers.IO) { block() }