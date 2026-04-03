package de.sh3n.kmp_starter_project.data.model

import de.sh3n.kmp_starter_project.domain.model.Greeting

fun GreetingDto.toDomain(): Greeting = Greeting(
    message = message,
    platform = platform,
)
