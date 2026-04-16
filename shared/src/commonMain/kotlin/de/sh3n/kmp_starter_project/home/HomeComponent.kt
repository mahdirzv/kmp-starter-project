package de.sh3n.kmp_starter_project.home

import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val greeting: String = "",
)

interface HomeComponent {
    val uiState: StateFlow<HomeUiState>
}
