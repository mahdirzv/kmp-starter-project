package de.sh3n.kmp_starter_project.tasks.data

import de.sh3n.kmp_starter_project.tasks.domain.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskLocalDataSource {
    val tasks: StateFlow<List<Task>>
    fun getTask(id: String): Task?
    suspend fun upsert(task: Task)
    suspend fun delete(taskId: String)
    suspend fun toggle(taskId: String)
}
