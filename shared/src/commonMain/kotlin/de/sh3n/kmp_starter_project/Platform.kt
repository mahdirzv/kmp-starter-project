package de.sh3n.kmp_starter_project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform