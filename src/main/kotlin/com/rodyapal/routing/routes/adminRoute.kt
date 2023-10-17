package com.rodyapal.routing.routes

import com.rodyapal.model.entity.User
import com.rodyapal.model.repository.BankAccountRepository
import com.rodyapal.model.repository.PersonRepository
import com.rodyapal.model.repository.TransactionRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
private data class ClientDto(val email: String, val passportNumber: String)
fun Route.adminRoute() {
	post("/create/user") {
		val user = call.receive<User>()
		PersonRepository.createUser(user).let {
			call.respond(
				status = HttpStatusCode.Created,
				message = Json.encodeToString(it)
			)
		}
	}
	post("/create/client") {
		val client = call.receive<ClientDto>()
		PersonRepository.createClient(
			email = client.email,
			clientPassportNumber = client.passportNumber
		).let {
			call.respond(
				status = HttpStatusCode.Created,
				message = Json.encodeToString(it)
			)
		}
	}
	post("/create/manager") {
		val email = call.receive<String>()
		PersonRepository.createManager(email).let {
			call.respond(
				status = HttpStatusCode.Created,
				message = Json.encodeToString(it)
			)
		}
	}

	get("/show/client") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				PersonRepository.allClients()
			)
		)
	}
	get("/show/transactions") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				TransactionRepository.allTransactions()
			)
		)
	}
	get("/show/bankAccounts") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				BankAccountRepository.allAccounts()
			)
		)
	}
	get("/show/debitCards") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				BankAccountRepository.allDebitCards()
			)
		)
	}

	post("/block/user") {
		PersonRepository.blockUser(
			id = call.receive<Long>()
		).let {
			when (it) {
				true -> call.respond(
					status = HttpStatusCode.OK,
					message = "User blocked"
				)
				false -> call.respond(
					status = HttpStatusCode.BadRequest,
					message = "Failed to block"
				)
			}
		}
	}
	post("/unblock/user") {
		PersonRepository.unblockUser(
			id = call.receive<Long>()
		).let {
			when (it) {
				true -> call.respond(
					status = HttpStatusCode.OK,
					message = "User unblocked"
				)
				false -> call.respond(
					status = HttpStatusCode.BadRequest,
					message = "Failed to unblock"
				)
			}
		}
	}
}