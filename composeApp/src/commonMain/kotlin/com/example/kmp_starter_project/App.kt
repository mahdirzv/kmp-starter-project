package com.example.kmp_starter_project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kmp_starter_project.root.RootComponent
import com.example.kmp_starter_project.root.ui.RootContent
import com.example.kmp_starter_project.ui.theme.AppTheme

@Composable
fun App(rootComponent: RootComponent) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            RootContent(component = rootComponent)
        }
    }
}
