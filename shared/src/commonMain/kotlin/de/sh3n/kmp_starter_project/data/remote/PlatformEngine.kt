package de.sh3n.kmp_starter_project.data.remote

import io.ktor.client.engine.HttpClientEngineFactory

expect fun platformEngine(): HttpClientEngineFactory<*>
