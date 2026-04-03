package de.sh3n.kmp_starter_project.di

import de.sh3n.kmp_starter_project.auth.AuthConfig
import de.sh3n.kmp_starter_project.data.remote.ApiService
import de.sh3n.kmp_starter_project.data.remote.createHttpClient
import de.sh3n.kmp_starter_project.data.remote.platformEngine
import de.sh3n.kmp_starter_project.data.repository.GreetingRepositoryImpl
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import de.sh3n.kmp_starter_project.presentation.GreetingViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single<AuthConfig> { AuthConfig.Disabled }
    single {
        createHttpClient(
            authConfig = get(),
            engine = platformEngine(),
        )
    }
    singleOf(::ApiService)
}

val repositoryModule = module {
    single<GreetingRepository> { GreetingRepositoryImpl(get()) }
}

val viewModelModule = module {
    factoryOf(::GreetingViewModel)
}

val sharedModules = listOf(networkModule, repositoryModule, viewModelModule)
