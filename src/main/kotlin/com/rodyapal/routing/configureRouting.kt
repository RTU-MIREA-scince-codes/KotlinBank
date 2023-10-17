package com.rodyapal.routing

import com.rodyapal.routing.routes.adminRoute
import com.rodyapal.routing.routes.clientRoute
import com.rodyapal.routing.routes.managerRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	routing {
		route("/admin") {
			adminRoute()
		}
		route("/client") {
			clientRoute()
		}
		route("/manager") {
			managerRoute()
		}
	}
}