package com.rodyapal.model.repository

import com.rodyapal.model.dbQuery
import com.rodyapal.model.entity.*
import com.rodyapal.utils.randomString

object BankAccountRepository {
	suspend fun byClient(clientNumber: String): List<BankAccount> = dbQuery {
		val client = ClientDao.find { Clients.clientNumber eq clientNumber }.first()
		BankAccountDao.find { BankAccounts.client eq client.id }.map { it.toEntity() }
	}

	suspend fun byAccountId(id: Long) = dbQuery {
		BankAccountDao.findById(id)?.toEntity()
	}

	suspend fun createAccount(clientNumber: String) = dbQuery {
		val forClient = ClientDao.find { Clients.clientNumber eq clientNumber }.first()
		BankAccountDao.new {
			accountNumber = randomString(32)
			balance = 0.0
			active = true
			client = forClient
		}.toEntity()
	}

	suspend fun allAccounts() = dbQuery {
		BankAccountDao.all().map { it.toEntity() }
	}

	suspend fun allDebitCards() = dbQuery {
		DebitCardDao.all().map { it.toEntity() }
	}
}