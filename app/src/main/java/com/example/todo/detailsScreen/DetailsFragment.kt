package com.example.todo.detailsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todo.databinding.FragmentDetailsBinding
import com.example.todo.database.dataClass.TaskJournal
import com.example.todo.uistateData.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


// DetailsFragment ->
// Här skrivs in ny title, description och checkBox och sparas i funktionen saveTaskJournal som finns i DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel:DetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = DetailsViewModel(requireContext())

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        var taskJournal: TaskJournal





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
            val taskTitle = binding.titleInput.text.toString()
            val taskDescription = binding.descriptionInput.text.toString()
            val taskIsChecked = binding.checkBoxDone.isChecked

           taskJournal = TaskJournal(taskTitle, taskDescription, taskIsChecked)
            viewModel.saveTaskJournal(taskJournal)


        }

         viewLifecycleOwner.lifecycleScope.launch {
             lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                 viewModel.saveTaskJournal(TaskJournal())
             }
         }








        return view
    }



}