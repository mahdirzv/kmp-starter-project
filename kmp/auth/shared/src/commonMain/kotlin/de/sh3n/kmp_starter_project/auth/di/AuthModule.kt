package de.sh3n.kmp_starter_project.auth.di

import de.sh3n.kmp_starter_project.auth.data.NoOpAuthRepository
import de.sh3n.kmp_starter_project.auth.domain.AuthRepository
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { NoOpAuthRepository() }
}
