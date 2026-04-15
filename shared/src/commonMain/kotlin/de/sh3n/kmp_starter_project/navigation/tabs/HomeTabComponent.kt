package de.sh3n.kmp_starter_project.navigation.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import de.sh3n.kmp_starter_project.presentation.home.HomeComponent

interface HomeTabComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
    }
}
