package de.sh3n.kmp_starter_project.presentation.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import de.sh3n.kmp_starter_project.domain.repository.GreetingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val greetingRepository: GreetingRepository,
    private val onItemSelected: (String) -> Unit,
) : HomeComponent, ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _uiState = MutableStateFlow(HomeUiState())
    override val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        lifecycle.doOnDestroy { scope.cancel() }
    }

    override fun onLoadGreeting() {
        scope.launch {
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
                    _uiState.value = HomeUiState(
                        message = greeting.message,
                        platform = greeting.platform,
                        isLoading = false,
                    )
                }
        }
    }

    override fun onNavigateToDetail(itemId: String) {
        onItemSelected(itemId)
    }
}
