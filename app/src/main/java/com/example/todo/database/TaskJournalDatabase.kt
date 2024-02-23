package com.example.todo.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.database.dataClass.TaskJournal

// Migration ->  när man lägger till en column i databasen i efterhand, uppdatera versionen till 2


@Database(
    entities = [TaskJournal::class],
    version = 2,
    //autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = false
)
abstract class TaskJournalDatabase: RoomDatabase(){

    abstract fun taskJournalDao(): TaskJournalDao
}