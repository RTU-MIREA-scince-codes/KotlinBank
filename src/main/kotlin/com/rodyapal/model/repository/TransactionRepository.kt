package com.rodyapal.model.repository

import com.rodyapal.model.dbQuery
import com.rodyapal.model.entity.*
import com.rodyapal.utils.randomString
import kotlinx.datetime.*

object TransactionRepository {
	suspend fun commitTransaction(
		fromId: Long,
		toId: Long,
		amount: Double
	) = dbQuery {
		val from = BankAccountDao.findById(fromId)!!
		val to = BankAccountDao.findById(toId)!!
		val fromClient = ClientDao.findById(from.id)!!
		TransactionDao.new {
			transactionNumber = randomString(32)
			transactionAmount = amount
			transactionDate = Clock.System.now().toLocalDateTime(timeZone = TimeZone.UTC)
			client = fromClient
			manager = fromClient.manager
			bankAccountFrom = from
			bankAccountTo = to
		}.toEntity()
	}

	suspend fun history(clientNumber: String) = dbQuery {
		val client = ClientDao.find { Clients.clientNumber eq clientNumber }.first()
		TransactionDao.find { Transactions.client eq client.id }.map { it.toEntity() }
	}

	suspend fun allTransactions() = dbQuery {
		TransactionDao.all().map { it.toEntity() }
	}
}