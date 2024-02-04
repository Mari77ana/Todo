package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Task
import com.example.todo.databinding.ItemsToReviewBinding
import com.example.todo.viewmodel.TaskViewModel


class TaskAdapter(
    private val todos: MutableList<Task> = mutableListOf(),
    // pass lambda to use clickListener
    private val onRemoveClicked: (Task) -> Unit,  // titta på den , item click namn ge den
    private val onCheckBoxChanged: (Task, Boolean) -> Unit,
    private val onItemClickListener:  (Task) -> Unit // ge ett argument i FirstFragment
) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    // ViewHolder -> Define components in view
    inner class MyViewHolder(private val binding: ItemsToReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
         lateinit  var task: Task

        init{

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                onCheckBoxChanged(task, isChecked)

            }
            binding.deleteTrash.setOnClickListener {
                onRemoveClicked(task) // lambda remove item
            }
            // making todoText clickable, take the root for parent layout
            binding.root.setOnClickListener {
                onItemClickListener(task)
            }

        }

        fun bind(item: Task) {
            task = item
            binding.todoText.text = item.todoText // binding text to view, checkbox, button
            binding.checkBox.isChecked = item.isChecked
        }


    }

    // Creates Layout Content for each item in recycle,
    // ansvarar för att skapa och returnera en ny ViewHolder-instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.MyViewHolder {

        val view = ItemsToReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: TaskAdapter.MyViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }


    fun updateData(list: List<Task>) {
        todos.clear()
        todos.addAll(list)
        notifyDataSetChanged() // function refer to Adapter, use when data changes in Ui (RecycleView)

    }
}