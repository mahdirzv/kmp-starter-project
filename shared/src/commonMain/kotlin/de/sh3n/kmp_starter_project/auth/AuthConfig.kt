package de.sh3n.kmp_starter_project.auth

sealed interface AuthConfig {
    data object Disabled : AuthConfig
    data object Anonymous : AuthConfig
    data class Bearer(
        val loadTokens: suspend () -> TokenPair?,
        val refreshTokens: suspend () -> TokenPair?,
    ) : AuthConfig
}

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)
