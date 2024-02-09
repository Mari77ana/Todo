package com.example.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.database.dataClass.TaskJournal
import kotlinx.coroutines.flow.Flow


// Data Access Object -> Dao
// For synchronised -> suspend

// Our Queries
// SQL- questions as delete, update, insert
@Dao
interface TaskJournalDao {

    @Insert() // sending taskJournal to Db
    suspend fun insertTaskJournal(taskJournal: TaskJournal)

    @Delete // Delete taskJournal in Db
    suspend fun deleteTaskJournal(taskJournal: TaskJournal)



     // check Flow
    // Get task from TaskJournal   (to use for showing in FirstFragment use a List of TaskJournal)
    @Query("SELECT * FROM task_journal ")
    fun observeAllTaskJournal(): Flow<List<TaskJournal>> // return a List to save all tasks


    // Get id from TaskJournal
    @Query("SELECT * FROM task_journal WHERE id = :taskId")
    fun getTaskJournalById(taskId: Long): Flow<List<TaskJournal>>


    // Gör en update query









}