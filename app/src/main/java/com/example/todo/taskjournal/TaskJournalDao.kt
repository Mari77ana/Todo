package com.example.todo.taskjournal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
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



}