package com.rodyapal.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

fun Application.configureApplication() {
	val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
	install(MicrometerMetrics) {
		registry = appMicrometerRegistry
		// ...
	}
	install(ContentNegotiation) {
		json()
	}
	routing {
		get("/metrics-micrometer") {
			call.respond(appMicrometerRegistry.scrape())
		}
	}
}