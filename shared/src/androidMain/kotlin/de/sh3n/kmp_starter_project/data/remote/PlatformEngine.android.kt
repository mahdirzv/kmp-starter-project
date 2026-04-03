package de.sh3n.kmp_starter_project.data.remote

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun platformEngine(): HttpClientEngineFactory<*> = OkHttp
