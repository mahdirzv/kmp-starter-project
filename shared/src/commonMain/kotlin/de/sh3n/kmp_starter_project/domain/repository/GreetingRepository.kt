package de.sh3n.kmp_starter_project.domain.repository

import de.sh3n.kmp_starter_project.domain.model.Greeting
import kotlinx.coroutines.flow.Flow

interface GreetingRepository {
    fun getGreeting(): Flow<Greeting>
}
