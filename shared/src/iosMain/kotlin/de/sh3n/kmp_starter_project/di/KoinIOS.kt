package de.sh3n.kmp_starter_project.di

import com.arkivanov.decompose.ComponentContext
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import de.sh3n.kmp_starter_project.navigation.DefaultRootComponent
import de.sh3n.kmp_starter_project.navigation.RootComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModules)
    }
}

object KoinHelper : KoinComponent {
    fun createRootComponent(componentContext: ComponentContext): RootComponent =
        DefaultRootComponent(
            componentContext = componentContext,
            greetingRepository = get<GreetingRepository>(),
        )
}
