package de.sh3n.kmp_starter_project.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.sh3n.kmp_starter_project.presentation.home.HomeComponent
import de.sh3n.kmp_starter_project.ui.theme.Sizing
import de.sh3n.kmp_starter_project.ui.theme.Spacing

@Composable
fun HomeScreen(component: HomeComponent, modifier: Modifier = Modifier) {
    val uiState by component.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(Sizing.iconXl),
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            uiState.error != null -> {
                Text(
                    text = uiState.error.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                )
                Spacer(modifier = Modifier.height(Spacing.md))
                Button(onClick = { component.onLoadGreeting() }) {
                    Text("Retry")
                }
            }

            uiState.message.isNotEmpty() -> {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text(
                    text = uiState.platform,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Spacer(modifier = Modifier.height(Spacing.lg))
                Button(onClick = { component.onNavigateToDetail("sample-item") }) {
                    Text("View Detail")
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Button(onClick = { component.onLoadGreeting() }) {
                        Text("Load Greeting")
                    }
                }
            }
        }
    }
}
