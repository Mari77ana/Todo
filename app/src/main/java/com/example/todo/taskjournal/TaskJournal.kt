package com.example.todo.taskjournal

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room setup

// Entity pushes to database / (Primary constructor)
@Entity(tableName = "task_journal")
data class TaskJournal (
    val title: String,
    val description: String,
    var status: Boolean,

    // outside the constructor ?
    // auto increment automatically ID:s depends on how many tasks creates
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null  // its null from the beginning

)





