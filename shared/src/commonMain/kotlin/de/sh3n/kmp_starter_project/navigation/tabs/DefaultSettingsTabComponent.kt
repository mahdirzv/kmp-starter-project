package de.sh3n.kmp_starter_project.navigation.tabs

import com.arkivanov.decompose.ComponentContext
import de.sh3n.kmp_starter_project.presentation.settings.DefaultSettingsComponent
import de.sh3n.kmp_starter_project.presentation.settings.SettingsComponent

class DefaultSettingsTabComponent(
    componentContext: ComponentContext,
) : SettingsTabComponent, ComponentContext by componentContext {

    override val component: SettingsComponent = DefaultSettingsComponent(
        componentContext = componentContext,
    )
}
