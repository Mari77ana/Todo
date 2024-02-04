package com.example.todo.data

import android.widget.ImageButton
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    val todoText: String,
    var isChecked: Boolean )

