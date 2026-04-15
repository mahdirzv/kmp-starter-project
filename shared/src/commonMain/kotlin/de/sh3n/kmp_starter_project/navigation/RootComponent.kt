package de.sh3n.kmp_starter_project.navigation

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import de.sh3n.kmp_starter_project.navigation.tabs.HomeTabComponent
import de.sh3n.kmp_starter_project.navigation.tabs.SettingsTabComponent

interface RootComponent {
    val pages: Value<ChildPages<*, Tab>>

    fun selectTab(index: Int)

    sealed class Tab {
        class Home(val component: HomeTabComponent) : Tab()
        class Settings(val component: SettingsTabComponent) : Tab()
    }
}
