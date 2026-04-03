package de.sh3n.kmp_starter_project.data.remote

import de.sh3n.kmp_starter_project.data.model.GreetingDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val httpClient: HttpClient) {

    suspend fun getGreeting(): GreetingDto =
        httpClient.get("/greeting").body()
}
