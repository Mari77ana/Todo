package com.example.todo.uistateData

data class Task(
    val id: Long,
    val todoText: String,
    var isChecked: Boolean
)

