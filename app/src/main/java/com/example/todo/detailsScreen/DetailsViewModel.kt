package com.example.todo.detailsScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.TaskJournalRepository
import com.example.todo.database.dataClass.TaskJournal
import com.example.todo.uistateData.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


class DetailsViewModel(context: Context) : ViewModel() {


    // använd singelton -> TaskJournalRepository.getInstance(context)
    private val taskJournalRepository = TaskJournalRepository.getInstance(context)

    private val _uistate = MutableStateFlow(UiState(null))
    val uiState = _uistate.asStateFlow()

    // vyn får ett id, skicka in id till vymodellen, vymodellen lyssnar på id och uppdaterar uistate, vyn lyssnar på uistate


    // adds task to Db
    fun saveTaskJournal(taskJournal: TaskJournal) {
        viewModelScope.launch {
            //faktiskt uppdatera tasken, inte bara skapa en ny
            taskJournalRepository.insertTaskJournal(taskJournal)
        }
    }

    fun setTaskId(id: Long) {
        viewModelScope.launch {
            taskJournalRepository.observeTaskJournalById(id).filterNotNull().collect { task ->
                _uistate.value = UiState(Task(task.id ?: 0L, task.title, task.status))
            }
        }
    }

    data class UiState(
        val task: Task?
    )
}