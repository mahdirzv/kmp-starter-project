package de.sh3n.kmp_starter_project.tasks.data

import de.sh3n.kmp_starter_project.tasks.domain.Task
import kotlinx.coroutines.flow.StateFlow

class DefaultTaskRepository(
    private val localDataSource: TaskLocalDataSource,
) : TaskRepository {
    override val tasks: StateFlow<List<Task>> = localDataSource.tasks

    override fun getTask(id: String): Task? = localDataSource.getTask(id)

    override suspend fun addTask(task: Task) {
        localDataSource.upsert(task)
    }

    override suspend fun removeTask(taskId: String) {
        localDataSource.delete(taskId)
    }

    override suspend fun toggleTask(taskId: String) {
        localDataSource.toggle(taskId)
    }
}
