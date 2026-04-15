package de.sh3n.kmp_starter_project.navigation.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import de.sh3n.kmp_starter_project.presentation.home.DefaultHomeComponent
import kotlinx.serialization.Serializable

class DefaultHomeTabComponent(
    componentContext: ComponentContext,
) : HomeTabComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, HomeTabComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): HomeTabComponent.Child =
        when (config) {
            is Config.Home -> HomeTabComponent.Child.Home(
                DefaultHomeComponent(componentContext = childComponentContext)
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config
    }
}
