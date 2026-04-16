package com.example.kmp_starter_project

import android.app.Application
import com.example.kmp_starter_project.di.sharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StarterApp)
            modules(sharedModules)
        }
    }
}
