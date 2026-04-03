package de.sh3n.kmp_starter_project.data.remote

import de.sh3n.kmp_starter_project.auth.AuthConfig
import de.sh3n.kmp_starter_project.util.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    authConfig: AuthConfig,
    engine: io.ktor.client.engine.HttpClientEngineFactory<*>,
): HttpClient = HttpClient(engine) {
    installDefaults()
    installAuth(authConfig)
}

private fun HttpClientConfig<*>.installDefaults() {
    expectSuccess = true

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = false
        })
    }

    install(Logging) {
        level = LogLevel.HEADERS
    }

    defaultRequest {
        url(AppConfig.baseUrl)
    }
}

private fun HttpClientConfig<*>.installAuth(authConfig: AuthConfig) {
    when (authConfig) {
        is AuthConfig.Disabled -> Unit
        is AuthConfig.Anonymous -> Unit
        is AuthConfig.Bearer -> {
            install(Auth) {
                bearer {
                    loadTokens {
                        authConfig.loadTokens()?.let {
                            BearerTokens(it.accessToken, it.refreshToken)
                        }
                    }
                    refreshTokens {
                        authConfig.refreshTokens()?.let {
                            BearerTokens(it.accessToken, it.refreshToken)
                        }
                    }
                }
            }
        }
    }
}
