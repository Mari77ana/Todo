package com.example.todo.viewmodel

import android.util.Log
import android.widget.CheckBox
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.todo.DetailsFragment
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {

    private val _uistate =
        MutableStateFlow(UiState(listOf())) // from inside the adapter, always private
    val uiState = _uistate.asStateFlow() // from outside ui

    init {
        // First call when the viewModel is instantiate, the first thing we see on screen -> init

        val myTask = mutableListOf<Task>()
        myTask.add(Task(0, "Buy Food", false))
        myTask.add(Task(1, "Complete next Academy task", false))
        myTask.add(Task(2, "Get some sleep", false))
        myTask.add(Task(3, "Read instructions", false))


        // updates task to mutableList
        _uistate.update { currentTaskState ->
            currentTaskState.copy(
                tasks = currentTaskState.tasks.toMutableList().apply {
                    addAll(myTask) // pass myTask mutableListOf

                }

            )

        }

    }

    // update tasks newList  with uiState, using when changing later to addItem or removeItem
    fun upDateTaskItemState(list: List<Task>) {
        _uistate.value = _uistate.value.copy(
            tasks = list.sortedWith(compareBy<Task> { it.isChecked }.thenBy { it.id })) // checked task automatically to bottom


    }

    fun addItem(item: Task) {
        _uistate.value = UiState(mutableListOf<Task>().apply {
            addAll(_uistate.value.tasks)
            add(item)
        })
    }

    fun deleteItem(item: Task) {
        val updateList =
            _uistate.value.tasks.toMutableList() // get the list with items for removing item
        updateList.remove(item)
        upDateTaskItemState(updateList) // then update the list
    }




    // modifying the  checkBox, take
    fun modifier(item: Task, newValue: Boolean) {  // use list and apply changes
        val newList = _uistate.value.tasks.toMutableList().apply {

            val index = indexOf(item)  // witch item we use
            remove(item)  // remove the chose item
            add(index, item.copy(isChecked = newValue)) // add the checked marked item
        }
        upDateTaskItemState(newList) // update the list

    }


    fun checkedBoxToBottom() {
        // todo check checkedTask
        // använd id

        // todo , Do I have to check uncheckedTasks ?
        // uncheckedTasks
        val uncheckedTasks = _uistate.value.tasks.sortedBy { !it.isChecked }

        //todo update list
        //val updateList = mutableListOf<Task>()^

        //upDateTaskItemState(uncheckedTasks)

    }


}