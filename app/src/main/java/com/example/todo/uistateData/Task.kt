package com.example.todo.uistateData

data class Task(
    val id: Long,
    val todoText: String,
    val description: String,
    var isChecked: Boolean
)

