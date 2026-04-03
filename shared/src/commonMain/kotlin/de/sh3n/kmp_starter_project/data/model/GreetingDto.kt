package de.sh3n.kmp_starter_project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GreetingDto(
    val message: String,
    val platform: String,
)
