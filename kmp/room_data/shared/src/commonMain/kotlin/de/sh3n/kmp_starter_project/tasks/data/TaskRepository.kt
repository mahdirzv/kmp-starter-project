package de.sh3n.kmp_starter_project.tasks.data

import de.sh3n.kmp_starter_project.tasks.domain.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>
    fun getTask(id: String): Task?
    suspend fun addTask(task: Task)
    suspend fun removeTask(taskId: String)
    suspend fun toggleTask(taskId: String)
}
