package com.rodyapal

import com.rodyapal.config.*
import com.rodyapal.routing.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
	@Test
	fun testRoot() = testApplication {
		application {
			configureApplication()
			configureRouting()
		}
		client.get("/").apply {
			assertEquals(HttpStatusCode.OK, status)
			assertEquals("Hello World!", bodyAsText())
		}
	}
}
