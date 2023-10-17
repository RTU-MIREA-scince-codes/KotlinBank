package com.rodyapal

import com.rodyapal.model.databaseInstance
import com.rodyapal.config.*
import com.rodyapal.routing.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main()  {
	embeddedServer(Netty, port = 10002, host = "0.0.0.0", module = Application::module)
		.start(wait = true)
}

fun Application.module() {
	databaseInstance
	configureApplication()
	configureRouting()
}
