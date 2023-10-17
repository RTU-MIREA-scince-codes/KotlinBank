package com.rodyapal.routing.routes

import com.rodyapal.model.repository.BankAccountRepository
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
private data class TransactionDto(
	val bankAccountId: Long,
	val bankAccountToId: Long,
	val amount: Double
)
fun Route.clientRoute() {
	get("/show/bankAccounts/{clientNumber}") {
		BankAccountRepository.byClient(call.parameters["clientNumber"] ?: "").let {
			if (it.isEmpty()) {
				call.respond(
					status = HttpStatusCode.BadRequest,
					message = "No accounts for number"
				)
			} else {
				call.respond(
					status = HttpStatusCode.OK,
					message = Json.encodeToString(it)
				)
			}
		}
	}
	get("/create/bankAccount/{clientNumber}") {
		BankAccountRepository.createAccount(
			call.parameters["clientNumber"] ?: return@get call.respond(
				status = HttpStatusCode.BadRequest,
				message = "Invalid client number"
			)
		).let {
			call.respond(
				status = HttpStatusCode.Created,
				message = Json.encodeToString(it)
			)
		}
	}
	post("/transaction") {
		val dto = call.receive<TransactionDto>()
		TransactionRepository.commitTransaction(
			fromId = dto.bankAccountId,
			toId = dto.bankAccountToId,
			amount = dto.amount
		).let {
			if (it != null) {
				call.respond(
					status = HttpStatusCode.Created,
					message = "Transaction ${it.transactionNumber} completed successfully"
				)
			} else call.respond(
				status = HttpStatusCode.BadRequest,
				message = "Transaction cannot be commited"
			)
		}
	}
	get("/show/transaction/{clientNumber}") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				TransactionRepository.history(
					clientNumber = call.parameters["clientNumber"] ?: ""
				)
			)
		)
	}
}