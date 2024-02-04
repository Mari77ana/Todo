package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Task
import com.example.todo.databinding.FragmentFirstBinding
import com.example.todo.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding

    val viewModel: TaskViewModel = TaskViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup binding
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val taskAdapter = TaskAdapter(onRemoveClicked = { viewModel.deleteItem(it) },
            onCheckBoxChanged = { task, value -> viewModel.modifier(task, value) },
            onItemClickListener = {task -> (activity as TaskActivity).showDetails(task) })



        binding.fabAddTask.setOnClickListener {

            viewModel.addItem(Task(4, "Shopping", false))

        }



        // Listener when call state to show in view then stop
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    taskAdapter.updateData(it.tasks)
                }
            }
        }

        val recycleView: RecyclerView = binding.recyclerView
        recycleView.adapter = taskAdapter










        return view

    }


}