package com.rodyapal

import com.rodyapal.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) : Unit = EngineMain.main(args)

fun Application.module() {
	configureMonitoring()
	configureSerialization()
	configureDatabases()
	configureRouting()
}
