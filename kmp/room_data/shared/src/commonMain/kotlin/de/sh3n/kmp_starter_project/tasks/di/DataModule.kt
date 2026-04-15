package de.sh3n.kmp_starter_project.tasks.di

import de.sh3n.kmp_starter_project.tasks.data.DefaultTaskRepository
import de.sh3n.kmp_starter_project.tasks.data.InMemoryTaskDataSource
import de.sh3n.kmp_starter_project.tasks.data.TaskLocalDataSource
import de.sh3n.kmp_starter_project.tasks.data.TaskRepository
import org.koin.dsl.module

val dataModule = module {
    single<TaskLocalDataSource> { InMemoryTaskDataSource() }
    single<TaskRepository> { DefaultTaskRepository(get()) }
}
