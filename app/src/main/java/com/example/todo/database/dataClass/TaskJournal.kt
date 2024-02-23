package com.example.todo.database.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Room setup

// Entity pushes to database /
@Entity(tableName = "task_journal")
//(Primary constructor)
data class TaskJournal (
    val title: String,
    val description: String,
    var status: Boolean,
    var comment: String,  // added in version 2

    // outside the constructor ?
    // auto increment automatically ID:s depends on how many tasks creates
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null  // its null from the beginning

)

// Migration from 1 to 2, Room 2.1.0.
val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE task_journal ADD COLUMN comment TEXT NOT NULL '' DEFAULT "
        )
    }

}





