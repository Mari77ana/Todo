package com.example.todo.database

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.todo.database.dataClass.TaskJournal
import com.example.todo.uistateData.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// TaskJournalRepository ->
// är en mellanhand mellan TaskJournalDatabase - TaskJournalDao -> TaskJournalViewModel
// Här kallar vi på suspend funktioner som finns i TaskJournalDao och kopplar det med TaskJournalDatabase


// S I N G L E T O N  = klassen kan bara instansieras en gång, blir global, (det ska inte skapas nya instanser av denna klass)
// singleton är bra att använda till databasanslutning, konfigurationshanterare el loggningstjänst

// @ V o l a t i l e  = Trådsäkerhet -> Trådsäkert vid ändringar i variabeln, syns i alla trådar

// C O M P A N I O N  = statisk meddlem i klassen som kan ha flera meddlemmar (exp funktioner).
// Man behöver inte instansiera klassen när man har companion i den klassen.

// ----------- C O U R U T I N E S ----------
// F L O W = jobbar asynkront så den inte blockerar huvudtråden, den observerar, kan vara av olika datatyper.
// Använd Flow med observer!!
// Den hanterar strömmar a data. Producer skickar data Consumer samlar data.
// ----- Taking a callback-----suspend & resume
// Suspend -> ( på pause) När den kallas kan den utföra asynkrona operationer  utan att blockera tråden
// Resume -> återupptar pausade courutine, kan återuppta där den har pausats
// ------------------------------------------

// CREATE DATABASE HERE IN REPOSITORY


// Singleton
class TaskJournalRepository private constructor(context: Context) {
// Singleton
    companion object {

        @Volatile //
        private var instance: TaskJournalRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: TaskJournalRepository(context).also { instance = it }
            }
    }


    // Build Database here
    private val taskJournalDatabase = Room.databaseBuilder(
        context, TaskJournalDatabase::class.java, "task journal"
    ).build()

// -----------------------------




    // Here we adding taskJournal to insertTaskJournal() to connect with TaskJournalDao
    suspend fun insertTaskJournal(taskJournal: TaskJournal) {
        Log.d("TaskJournalRepository", "Inserting task to database: $taskJournal")
        taskJournalDatabase.taskJournalDao().insertTaskJournal(taskJournal)

    }

    // Denna ska uppdatera objektet TaskJournal
    suspend fun updateTaskJournal(taskJournal: TaskJournal) {
        taskJournalDatabase.taskJournalDao().updateTaskJournal(taskJournal)
    }

    // Delete Task by Id
    suspend fun deleteTaskJournal(taskJournal: TaskJournal) {
        taskJournalDatabase.taskJournalDao().deleteTaskJournal(taskJournal)
    }



    // måste observera för ändringar -> Flow, returnera TaskJournal i en lista för att sedan visa upp i FirstFragment
    fun observeAllTaskJournals(): Flow<List<TaskJournal>> {
        return taskJournalDatabase.taskJournalDao().observeAllTaskJournal()
    }

    // funktionen Observerar bara ID och hämtar ID från databasen,(här behövs ingen Lista)
    fun observeTaskJournalById(taskId: Long): Flow<TaskJournal?> {
        return taskJournalDatabase.taskJournalDao().observeTaskJournalById(taskId).map { it.firstOrNull() }
    }


    suspend fun getTaskJournalById(id: Long): TaskJournal? {
        return taskJournalDatabase.taskJournalDao().getTaskJournalById(id).let { it.firstOrNull()  }
    }










}