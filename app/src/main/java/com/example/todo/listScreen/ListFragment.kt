package com.example.todo.listScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.TaskActivity
import com.example.todo.databinding.FragmentFirstBinding
import com.example.todo.detailsScreen.DetailsFragment
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModel: ListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ListViewModel(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // setup binding
        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        val taskAdapter = ListAdapter(
            onRemoveClicked = {
                viewModel.deleteItem(it)
            },
            onCheckBoxChanged = { task, value ->
                viewModel.modifier(task, value)
            },
            onItemClickListener = { task ->
                (activity as TaskActivity).showDetails(task)
            }
        )



        binding.fabAddTask.setOnClickListener {
            println("Fab pressed")
            //  navigate to write a new TaskJournal
            val bundle = Bundle()
            val detailsFragment = DetailsFragment()

            val activity = requireActivity() as TaskActivity // för att fragmentContainer ligger på activityn

            activity.showDetails(null)
        }

        // Listener when call state to show in view then stop, updates the view
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    taskAdapter.updateData(it.tasks) // copy it in the details fragment update the title description
                }
            }
        }


        val recycleView: RecyclerView = binding.recyclerView
        recycleView.adapter = taskAdapter










        return view

    }


}