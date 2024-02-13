package com.example.todo.listScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.uistateData.Task
import com.example.todo.database.dataClass.TaskJournal
import com.example.todo.database.TaskJournalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(context: Context) : ViewModel() {
    private val taskJournalRepository = TaskJournalRepository.getInstance(context)

    private val _uiState =
        MutableStateFlow(UiState(listOf())) // from inside the adapter, always private
    val uiState = _uiState.asStateFlow() // from outside ui


    // First call when the viewModel is instantiate, the first thing we see on screen -> init
    init {
        // Denna hämtar alla taskJournal från databasen med map taskJournal konverteras till Task
        taskJournalRepository.observeAllTaskJournals().onEach { taskJournals ->

            val tasks: List<Task> = taskJournals.map { taskJournal: TaskJournal ->
                Task(taskJournal.id ?: 0, taskJournal.title, taskJournal.description,taskJournal.status)
            }
            _uiState.update { currentTaskState ->
                currentTaskState.copy(tasks = tasks)
            }

        }.launchIn(viewModelScope)


    }// init ends


    // update tasks newList  with uiState, using when changing later to addItem or removeItem
    fun upDateTaskItemState(list: List<Task>) {
        _uiState.value = _uiState.value.copy(
            tasks = list.sortedWith(compareBy<Task> { it.isChecked }.thenBy { it.id })
        ) // checked task automatically to bottom


    }


    // add Task , add data in database,
    fun addItem(item: Task) {
        // inserts the task from user / not hard coded
        viewModelScope.launch {
            taskJournalRepository.insertTaskJournal(
                TaskJournal(item.todoText, "", false)
            )
        }
    }


    fun deleteItem(item: Task) {
        viewModelScope.launch {
            taskJournalRepository.deleteTaskJournal(
                TaskJournal(item.todoText, "", item.isChecked, item.id)
            )
        }
    }


    // modifying the  checkBox, take
    fun modifier(item: Task, newValue: Boolean) {  // use list and apply changes
        val newList = _uiState.value.tasks.toMutableList().apply {

            val index = indexOf(item)  // witch item we use
            remove(item)  // remove the chose item
            add(index, item.copy(isChecked = newValue)) // add the checked marked item
        }
        upDateTaskItemState(newList) // update the list

    }


    fun checkedBoxToBottom() {

        // uncheckedTasks
        val uncheckedTasks = _uiState.value.tasks.sortedBy { !it.isChecked }

    }

    data class UiState(
        val tasks: List<Task>
    )
}