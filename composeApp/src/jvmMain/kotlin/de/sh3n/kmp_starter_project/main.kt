package de.sh3n.kmp_starter_project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import de.sh3n.kmp_starter_project.di.sharedModules
import de.sh3n.kmp_starter_project.root.DefaultRootComponent
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(sharedModules)
    }

    val lifecycle = LifecycleRegistry()
    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
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
