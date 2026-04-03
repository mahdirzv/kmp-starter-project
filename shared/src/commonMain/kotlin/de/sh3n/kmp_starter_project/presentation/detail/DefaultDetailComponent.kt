package de.sh3n.kmp_starter_project.presentation.detail

import com.arkivanov.decompose.ComponentContext

class DefaultDetailComponent(
    componentContext: ComponentContext,
    override val itemId: String,
    private val onBack: () -> Unit,
) : DetailComponent, ComponentContext by componentContext {

    override fun onNavigateBack() {
        onBack()
    }
}
