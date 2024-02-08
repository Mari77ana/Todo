package com.example.todo.database.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room setup

// Entity pushes to database /
@Entity(tableName = "task_journal")
//(Primary constructor)
data class TaskJournal (
    val title: String,
    val description: String,
    var status: Boolean,

    // outside the constructor ?
    // auto increment automatically ID:s depends on how many tasks creates
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null  // its null from the beginning

)





