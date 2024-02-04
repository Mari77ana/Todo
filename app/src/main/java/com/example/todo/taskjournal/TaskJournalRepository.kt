package com.example.todo.taskjournal

import com.example.todo.database.TaskJournalDatabase

// Denna klass är en mellanhand mellan TaskJournalDatabase - TaskJournalDao -> TaskJournalViewModel
// Här kallar vi på suspend funktioner som finns i TaskJournalDao och kopplar det med TaskJournalDatabase


class TaskJournalRepository(private val taskJournalDatabase: TaskJournalDatabase) {

     // Here we adding taskJournal to insertTaskJournal() to connect to with TaskJournalDao
    suspend fun insertTaskJournal(taskJournal: TaskJournal){
        taskJournalDatabase.taskJournalDao().insertTaskJournal(taskJournal)

    }

    // for deleting
    suspend fun deleteTaskJournal(taskJournal: TaskJournal){
        taskJournalDatabase.taskJournalDao().deleteTaskJournal(taskJournal)

    }






}