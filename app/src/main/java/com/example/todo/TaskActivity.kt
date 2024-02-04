package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.todo.data.Task
import com.example.todo.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // binding FirstFragment to TaskActivity to get an id for fragmentContainer
        val firstFragmentContainerView = binding.fragmentContainerView

        // starts the transaction for FirstFragment, use replace to find Id for fragmentContainer
        // use add() if adding another fragment
        val firstFragment = FirstFragment()
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, firstFragment)
        }



    }

    // trying to send data task to show up in DetailsFragment
    fun showDetails(task: Task){
        val bundle = Bundle() // use bundle to pass data between fragments
        bundle.putString( "task", task.todoText) // putserilizable()
        val detailsFragment = DetailsFragment()
        // denna måste med
        detailsFragment.arguments = bundle
        supportFragmentManager.commit {
            addToBackStack("Details")
            replace(R.id.fragmentContainerView, detailsFragment)

        }
    }
}