package de.sh3n.kmp_starter_project.settings

import com.arkivanov.decompose.ComponentContext

class DefaultSettingsComponent(
    componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext
