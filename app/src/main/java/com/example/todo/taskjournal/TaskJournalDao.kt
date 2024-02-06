package com.example.todo.taskjournal

import androidx.compose.ui.input.pointer.PointerId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todo.taskjournal.TaskJournal

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


    // Get task from TaskJournal   (to use for showing in FirstFragment use a List of TaskJournal)
    @Query("SELECT * FROM task_journal ")
    suspend fun getAllTaskJournal(): List<TaskJournal> // return a List to save all tasks in it






}