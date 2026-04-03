package de.sh3n.kmp_starter_project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import de.sh3n.kmp_starter_project.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class GreetingUiState(
    val message: String = "",
    val platform: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
)

class GreetingViewModel(
    private val greetingRepository: GreetingRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(GreetingUiState())
    val uiState: StateFlow<GreetingUiState> = _uiState.asStateFlow()

    fun loadGreeting() {
        viewModelScope.launch {
            greetingRepository.getGreeting()
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
                .collect { greeting ->
                    _uiState.value = GreetingUiState(
                        message = greeting.message,
                        platform = greeting.platform,
                        isLoading = false,
                    )
                }
        }
    }
}
