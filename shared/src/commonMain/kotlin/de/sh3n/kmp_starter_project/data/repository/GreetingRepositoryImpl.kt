package de.sh3n.kmp_starter_project.data.repository

import de.sh3n.kmp_starter_project.data.model.toDomain
import de.sh3n.kmp_starter_project.data.remote.ApiService
import de.sh3n.kmp_starter_project.domain.model.Greeting
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import de.sh3n.kmp_starter_project.getPlatform
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GreetingRepositoryImpl(
    private val apiService: ApiService,
) : GreetingRepository {

    override fun getGreeting(): Flow<Greeting> = flow {
        try {
            val dto = apiService.getGreeting()
            emit(dto.toDomain())
        } catch (_: Exception) {
            emit(Greeting(message = "Hello", platform = getPlatform().name))
        }
    }
}
