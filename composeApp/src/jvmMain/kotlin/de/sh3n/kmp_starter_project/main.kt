package de.sh3n.kmp_starter_project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kmpstarterproject",
    ) {
        App()
    }
}