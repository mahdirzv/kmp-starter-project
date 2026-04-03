package de.sh3n.kmp_starter_project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import de.sh3n.kmp_starter_project.di.sharedModules
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import de.sh3n.kmp_starter_project.navigation.DefaultRootComponent
import org.koin.core.context.startKoin

fun main() {
    val koin = startKoin {
        modules(sharedModules)
    }.koin

    val lifecycle = LifecycleRegistry()
    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        greetingRepository = koin.get<GreetingRepository>(),
    )

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KMP Starter",
        ) {
            App(rootComponent = root)
        }
    }
}
