package de.sh3n.kmp_starter_project.presentation.home

import kotlinx.coroutines.flow.StateFlow

data class HomeUiState(
    val message: String = "",
    val platform: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

interface HomeComponent {
    val uiState: StateFlow<HomeUiState>
    fun onLoadGreeting()
    fun onNavigateToDetail(itemId: String)
}
