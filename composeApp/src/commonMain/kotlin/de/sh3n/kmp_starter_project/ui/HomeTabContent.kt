package de.sh3n.kmp_starter_project.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import de.sh3n.kmp_starter_project.navigation.tabs.HomeTabComponent
import de.sh3n.kmp_starter_project.ui.screens.home.HomeScreen

@Composable
fun HomeTabContent(component: HomeTabComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is HomeTabComponent.Child.Home -> HomeScreen(component = child.component)
        }
    }
}
