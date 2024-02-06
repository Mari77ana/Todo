package com.example.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentDetailsBinding
import com.example.todo.taskjournal.TaskJournal
import com.example.todo.viewmodel.TaskJournalViewModel


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel:TaskJournalViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = TaskJournalViewModel(requireContext())

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        var taskJournal:TaskJournal



        binding.myToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // sends taskText to textInput,   (sending data between Fragments)
        val task = arguments?.getString("task")
        if(task != null){
            binding.titleInput.setText(task)
        }

        // Add taskJournal when button is clicked
        binding.buttonSaveTaskJournal.setOnClickListener {
            val taskTitle = binding.titleInput.toString()
            val taskDescription = binding.descriptionInput.text.toString()
            val taskIsChecked = binding.checkBoxDone.isChecked

           taskJournal = TaskJournal(taskTitle, taskDescription, taskIsChecked)
            viewModel.saveTaskJournal(taskJournal)
        }





        return view
    }



}