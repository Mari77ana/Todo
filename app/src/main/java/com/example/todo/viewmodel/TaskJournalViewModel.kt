package com.example.todo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.todo.database.TaskJournalDatabase
import com.example.todo.taskjournal.TaskJournal
import com.example.todo.taskjournal.TaskJournalRepository
import kotlinx.coroutines.launch

class TaskJournalViewModel(context: Context): ViewModel() {

    // instantiate room database here and not in activity or fragment
    private val taskJournalDatabase =  Room.databaseBuilder(
        context,TaskJournalDatabase :: class.java, "task journal").build()

    // Instantiate repository here
    private val taskJournalRepository = TaskJournalRepository(taskJournalDatabase)



    fun saveTaskJournal(taskJournal: TaskJournal){
        viewModelScope.launch {
            taskJournalRepository.insertTaskJournal(taskJournal)
        }

    }

    fun getTasks(taskJournal: TaskJournal){
        viewModelScope.launch {
            taskJournalRepository.getAllTaskJournals()
        }

    }



}