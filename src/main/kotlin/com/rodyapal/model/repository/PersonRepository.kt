package com.rodyapal.model.repository

import com.rodyapal.model.dbQuery
import com.rodyapal.model.entity.*
import com.rodyapal.utils.randomString

object PersonRepository {
	suspend fun createUser(user: User) = dbQuery {
		UserDao.new {
			firstName = user.firstName
			lastName = user.lastName
			email = user.email
			telephoneNumber = user.telephoneNumber
			password = user.password
			active = user.active
		}.toEntity()
	}

	suspend fun createClient(email: String, clientPassportNumber: String) = dbQuery {
		val fromUser = UserDao.find { Users.email eq email }.first()
		ClientDao.new {
			clientNumber = randomString(32)
			passportNumber = clientPassportNumber
			user = fromUser
			manager = ManagerDao.all().toList().random()
		}.toEntity()
	}

	suspend fun createManager(email: String) = dbQuery {
		val fromUser = UserDao.find { Users.email eq email }.first()
		ManagerDao.new {
			managerNumber = randomString(32)
			user = fromUser
		}.toEntity()
	}

	suspend fun blockUser(id: Long) = dbQuery {
		val user = UserDao.findById(id)
		user?.active = false
		return@dbQuery user != null
	}

	suspend fun unblockUser(id: Long) = dbQuery {
		val user = UserDao.findById(id)
		user?.active = true
		return@dbQuery user != null
	}

	suspend fun allClients() = dbQuery {
		ClientDao.all().map { it.toEntity() }
	}
}