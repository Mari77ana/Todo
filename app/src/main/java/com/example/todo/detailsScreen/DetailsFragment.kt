package com.example.todo.detailsScreen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todo.databinding.FragmentDetailsBinding
import com.example.todo.database.dataClass.TaskJournal
import kotlinx.coroutines.launch


// DetailsFragment ->
// Här skrivs in ny title, description och checkBox och sparas i funktionen saveTaskJournal som finns i DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel


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
        viewModel.getFox()



        binding.myToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val id = arguments?.getLong("id") ?: -1 // hämtar id
        Log.d("FirstFragment", "$id")
        if (id != -1L) {
            viewModel.setTaskId(id) // skickar in id till viewModel
        }

        // Observera UI State och uppdaterar EditText när det finns uppgiftsdata
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    val task = uiState.task
                    Log.d("DetailsFragment", "$task")

                    if (task != null) {
                        binding.titleInput.setText(task.todoText)
                        binding.descriptionInput.setText(task.description)
                        binding.commentInput.setText(task.comment)
                        binding.checkBoxDone.isChecked = task.isChecked

                    } else {
                        binding.titleInput.setText("")
                        binding.descriptionInput.setText("")
                        binding.checkBoxDone.isChecked = false
                    }
                }
            }
        }


        // Add taskJournal when button Done is clicked,  uppdatera
        binding.buttonSaveTaskJournal.setOnClickListener {
            val taskTitle = binding.titleInput.text.toString()
            val taskDescription = binding.descriptionInput.text.toString()
            val taskComment = binding.commentInput.text.toString()
            val taskIsChecked = binding.checkBoxDone.isChecked

            viewModel.saveTaskJournal(
                TaskJournal(
                    taskTitle,
                    taskDescription,
                    taskIsChecked,
                    taskComment,
                    id.takeIf { id > -1 } // lade till denna annars uppdateras det inte
                )
            )

            // Check for new TaskJournal
            println(
                TaskJournal(
                    title = taskTitle,
                    description = taskDescription,
                    comment = taskComment,
                    status = taskIsChecked
                )
            )
            parentFragmentManager.popBackStack() // navigate to ListFragment
        }


        // Button Cancel
        binding.buttonCancel.setOnClickListener {
            // clear the textFields
            binding.titleInput.setText("")
            binding.descriptionInput.setText("")
            binding.commentInput.setText("")
            binding.checkBoxDone.isChecked = false
        }


        // Button Delete TaskJournal and show AlertDialog
        binding.buttonDelete.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete Task")
            builder.setMessage("Are you sure you want to delete Task")

            builder.setPositiveButton("Delete") { _, _ ->

                if (id != null) {
                    viewModel.deleteTaskJournal(id)
                    binding.titleInput.setText("")
                    binding.descriptionInput.setText("")
                    binding.commentInput.setText("")
                    binding.checkBoxDone.isChecked = false
                    parentFragmentManager.popBackStack() // Navigate to ListFragment

                }

            }

            builder.setNegativeButton("Cancel") { _, _ ->
                builder.setOnDismissListener { dialog -> dialog.dismiss() }

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()


        }









        return view
    }
}