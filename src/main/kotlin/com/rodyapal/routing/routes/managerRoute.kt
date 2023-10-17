package com.rodyapal.routing.routes

import com.rodyapal.model.repository.BankAccountRepository
import com.rodyapal.model.repository.TransactionRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.managerRoute() {
	get("/show/{bankAccountId}") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				BankAccountRepository.byAccountId(call.parameters["bankAccountId"]?.toLongOrNull() ?: -1L)
			)
		)
	}
	get("/show/{bankAccountId}/transactions/") {
		call.respond(
			status = HttpStatusCode.OK,
			message = Json.encodeToString(
				TransactionRepository.allTransactions().filter {
					it.bankAccountFrom.id == call.parameters["bankAccountId"]?.toLong() || it.bankAccountTo.id == call.parameters["bankAccountId"]?.toLong()
				}
			)
		)
	}
}