package com.example.todo.listScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.uistateData.Task
import com.example.todo.databinding.ItemsToReviewBinding


class ListAdapter(
    //private val todos: MutableList<Task> = mutableListOf(),
    // pass lambda to use clickListener
    private val onRemoveClicked: (Task) -> Unit,  // titta på den , item click namn ge den
    private val onCheckBoxChanged: (Task, Boolean) -> Unit,
    private val onItemClickListener:  (Task) -> Unit // ge ett argument i FirstFragment
) :
    androidx.recyclerview.widget.ListAdapter<Task, ListAdapter.MyViewHolder>(TaskDiffCallback) {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = ItemsToReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateData(list: List<Task>) {
        submitList(list)

    }
}

object TaskDiffCallback: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {

        return oldItem == newItem // compair the "object" to check if there is same items in the list
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        // if the content is the same  content just the id:s, dosen't matter if the content has changed
        return oldItem.id  == newItem.id

    }

}