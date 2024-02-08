package com.example.todo.detailsScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.database.TaskJournalRepository
import com.example.todo.database.dataClass.TaskJournal
import com.example.todo.uistateData.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// Denna DetailsViewModel sparar TaskJournal med function saveTaskJournal
class DetailsViewModel(context: Context): ViewModel() {


    // använd singelton -> TaskJournalRepository.getInstance(context)
    private val taskJournalRepository = TaskJournalRepository.getInstance(context)

  // create uistate for details. use a state flow to be observed by the fragment
  private val _uistate = MutableStateFlow(UiState(listOf()))
    val uiState = _uistate.asStateFlow()



    //Hämta Id från databasen
    fun getTask(taskId: Long) {
        viewModelScope.launch {
           taskJournalRepository.observeTaskJournalById(taskId)
        }


        // update uistate state flow with new data
    }



//    C O U R U T I N E S
    fun saveTaskJournal(taskJournal: TaskJournal){
        viewModelScope.launch {
            taskJournalRepository.insertTaskJournal(taskJournal)
        }

    }















}