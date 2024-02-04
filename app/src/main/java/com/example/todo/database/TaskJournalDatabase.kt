package com.example.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.taskjournal.TaskJournal
import com.example.todo.taskjournal.TaskJournalDao

@Database(entities = [TaskJournal::class], version = 1)
abstract class TaskJournalDatabase: RoomDatabase(){

    abstract fun taskJournalDao(): TaskJournalDao
}