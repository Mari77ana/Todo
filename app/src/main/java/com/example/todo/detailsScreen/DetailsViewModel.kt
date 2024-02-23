package com.example.todo.detailsScreen

import android.content.Context
import android.util.Log
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

    private val _uistate = MutableStateFlow(UiState(null)) // task är null
    val uiState = _uistate.asStateFlow()

    // vyn får ett id, skicka in id till vymodellen, vymodellen lyssnar på id och uppdaterar uistate, vyn lyssnar på uistate




    // adds task to Db
    fun saveTaskJournal(taskJournal: TaskJournal) {
        viewModelScope.launch {
            //faktiskt uppdatera tasken, inte bara skapa en ny
            // val existingTask = taskJournalRepository.insertTaskJournal(taskJournal)
            // hämta id och uppdatera
            val existingTask = taskJournalRepository.getTaskJournalById(taskJournal.id ?: -1)

            // Finns det en TaskJournal -> hämta kopia och uppdatera det nya värdet
            if (existingTask == null){

                taskJournalRepository.insertTaskJournal(taskJournal)
                Log.d("DetailsViewModel", "Inserting new task: $taskJournal")

            }
            // annars -> skapa en ny TaskJournal
            else{

                val updateTaskJournal = existingTask.copy(
                    id = taskJournal.id,
                    title = taskJournal.title,
                    description = taskJournal.description,
                    status = taskJournal.status
                )
                taskJournalRepository.updateTaskJournal(updateTaskJournal)
                Log.d("DetailsViewModel", "Updating existing task: $existingTask with new data: $taskJournal")

            }
        }
    }


    // observera id fr databasen, använd courutine med uistate för att uppdatera Task,
    // filterNotNull= filtrerar null värden från observationen
    fun setTaskId(id: Long) {
        viewModelScope.launch {

            taskJournalRepository.observeTaskJournalById(id).filterNotNull().collect { task ->
                //_uistate.value = UiState(Task(task.id ?: 0L, task.title, task.status))
                _uistate.value = UiState(
                    Task(
                        task.id ?: -1,
                        task.title,
                        task.description,
                        task.comment,
                        task.status
                    )
                )

            }

        }
    }


    // Delete Task by find id, // id: Long
    fun deleteTaskJournal(taskId: Long) {
        viewModelScope.launch {
            val existingTask = taskJournalRepository.getTaskJournalById(taskId)
            //val deleteTaskJournalById = taskJournalRepository.getTaskJournalById(id)
            if (existingTask != null) {
                taskJournalRepository.deleteTaskJournal(existingTask)
            }
        }

    }




    data class UiState(
        val task: Task? // kan vara null
    )



}
