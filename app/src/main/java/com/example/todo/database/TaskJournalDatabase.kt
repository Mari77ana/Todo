package com.example.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.database.dataClass.TaskJournal

@Database(entities = [TaskJournal::class], version = 1)
abstract class TaskJournalDatabase: RoomDatabase(){

    abstract fun taskJournalDao(): TaskJournalDao
}