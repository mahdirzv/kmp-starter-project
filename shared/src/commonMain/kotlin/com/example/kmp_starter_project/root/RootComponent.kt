package com.example.kmp_starter_project.root

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.value.Value
import com.example.kmp_starter_project.home.HomeComponent
import com.example.kmp_starter_project.settings.SettingsComponent

interface RootComponent {
    val pages: Value<ChildPages<*, Tab>>

    fun selectTab(index: Int)

    sealed class Tab {
        class Home(val component: HomeComponent) : Tab()
        class Settings(val component: SettingsComponent) : Tab()
    }
}
