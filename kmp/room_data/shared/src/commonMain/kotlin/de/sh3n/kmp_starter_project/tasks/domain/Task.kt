package de.sh3n.kmp_starter_project.tasks.domain

data class Task(
    val id: String,
    val title: String,
    val completed: Boolean = false,
)
