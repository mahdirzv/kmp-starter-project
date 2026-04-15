package de.sh3n.kmp_starter_project.presentation.home

import com.arkivanov.decompose.ComponentContext
import de.sh3n.kmp_starter_project.getPlatform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultHomeComponent(
    componentContext: ComponentContext,
) : HomeComponent, ComponentContext by componentContext {

    override val uiState: StateFlow<HomeUiState> = MutableStateFlow(
        HomeUiState(greeting = "Hello from ${getPlatform().name}")
    ).asStateFlow()
}
