package de.sh3n.kmp_starter_project

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

private object ServerConfig {
    const val PORT = 8080
    const val HOST = "0.0.0.0"
}

@Serializable
data class GreetingResponse(
    val message: String,
    val platform: String,
)

fun main() {
    embeddedServer(
        Netty,
        port = ServerConfig.PORT,
        host = ServerConfig.HOST,
        module = Application::module,
    ).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("KMP Starter Server")
        }

        get("/greeting") {
            call.respond(
                GreetingResponse(
                    message = "Hello from Ktor Server!",
                    platform = "Server (JVM)",
                ),
            )
        }
    }
}
