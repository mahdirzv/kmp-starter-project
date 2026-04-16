package com.example.kmp_starter_project.di

import com.arkivanov.decompose.ComponentContext
import com.example.kmp_starter_project.root.DefaultRootComponent
import com.example.kmp_starter_project.root.RootComponent
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModules)
    }
}

object KoinHelper : KoinComponent {
    fun createRootComponent(componentContext: ComponentContext): RootComponent =
        DefaultRootComponent(componentContext = componentContext)
}
