package com.example.greentipskotlin.App.CEO.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityUpdateTaskBinding

class UpdateTask : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding

    private val model: TaskViewModel by viewModels()
    private val empModel: EmployeeViewModel by viewModels()

    private var taskId: Int = -1
    private var estateId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize spinner adapter
        val estateNames = mutableListOf("Select a Estate")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Fetch estate names and add them to the spinner
        estateNames.addAll(empModel.getAllEstateNames())
        binding.estateSpinner.adapter = adapter

        // Retrieve task data from intent
        taskId = intent.getIntExtra("TASK_ID", -1)
        val taskName = intent.getStringExtra("Task_NAME") ?: ""
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION") ?: ""
        val taskType = intent.getStringExtra("TASK_TYPE") ?: ""
        val taskAssignDate = intent.getStringExtra("TASK_ASSIGN_DATE") ?: ""
        val taskDueDate = intent.getStringExtra("TASK_DUE_DATE") ?: ""
        val taskProgress = intent.getStringExtra("TASK_PROGRESS") ?: ""
        val taskChallenges = intent.getStringExtra("TASK_CHALLENGES") ?: ""
        val taskSolution = intent.getStringExtra("TASK_SOLUTION") ?: ""
        estateId = intent.getIntExtra("TASK_ESTATE_ID_FR", -1) // Get estate ID

        // Populate EditText fields
        binding.tvTaskId.text=taskId.toString()
        binding.teTaskName.setText(taskName)
        binding.teTaskDescription.setText(taskDescription)
        binding.teTaskType.setText(taskType)
        binding.teAssignTask.setText(taskAssignDate)
        binding.teDueDate.setText(taskDueDate)
        binding.tvCurrentChallenge.text="Current Challenge : $taskChallenges"
        binding.tvCurrentSolution.text="Current Solution : $taskSolution"
        binding.tvProgress.text="Current Progress : $taskProgress"

        populateEstateSpinner()

        loadAssignedWorkers()


        binding.updateTask.setOnClickListener{
            updateTask()
        }

        binding.deleteTask.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task and its assignments?")
                .setPositiveButton("Yes") { _, _ ->
                    // First, delete task assignments related to this task
                    model.deleteTaskAssignmentByTaskId(taskId)

                    // Then, delete the task itself
                    model.deleteTask(taskId)

                    Toast.makeText(this, "Task and assignments deleted successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity after deletion
                }
                .setNegativeButton("No", null)
                .show()
        }



        binding.updateSolution.setOnClickListener {
            val updatedSolution = binding.teTaskSolution.text.toString().trim()

            // Validate input
            if (taskId == -1 || updatedSolution.isEmpty()) {
                Toast.makeText(this, "Please provide a valid task ID and solution", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update the task solution in the database
            val updatedRows = model.updateTaskSolution(taskId, updatedSolution)

            if (updatedRows > 0) {
                Toast.makeText(this, "Task solution updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }

            finish() // Optionally close the activity
        }



    }

    private fun loadAssignedWorkers() {
        if (taskId == -1) return

        // Fetch assigned worker IDs from ViewModel
        val assignedWorkerIds = model.getWorkersByTaskId(taskId)

        if (assignedWorkerIds.isNotEmpty()) {
            val assignedWorkersList = assignedWorkerIds.map { workerId ->
                val workerName = model.getEmployeeNameById(workerId)
                "$workerId - $workerName"
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, assignedWorkersList)
            binding.lvAssignedWorkers.adapter = adapter
        } else {
            binding.lvAssignedWorkers.adapter = null
            Toast.makeText(this, "No workers assigned to this task", Toast.LENGTH_SHORT).show()
        }
    }



    private fun populateEstateSpinner() {
        val estateNames = mutableListOf("Select an Estate")
        val estates = empModel.getAllEstateNames()

        if (estates.isNullOrEmpty()) {
            Log.e("EstateError", "No estates available")
            Toast.makeText(this, "No estates found", Toast.LENGTH_SHORT).show()
            return
        }

        estateNames.addAll(estates)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.estateSpinner.adapter = adapter

        val estateIdA = estateId
        if (estateIdA != null && estateIdA in 1 until estateNames.size) {
            binding.estateSpinner.setSelection(estateIdA)
        } else {
            Log.e("SpinnerError", "Invalid estate ID: $estateIdA")
        }
    }

    private fun updateTask() {
        val taskId = binding.tvTaskId.text.toString().toIntOrNull() ?: -1
        val updatedTaskName = binding.teTaskName.text.toString().trim()
        val updatedTaskDescription = binding.teTaskDescription.text.toString().trim()
        val updatedTaskType = binding.teTaskType.text.toString().trim()
        val updatedTaskAssignDate = binding.teAssignTask.text.toString().trim()
        val updatedTaskDueDate = binding.teDueDate.text.toString().trim()
        val selectedEstateIndex = binding.estateSpinner.selectedItemPosition

        // Validate input
        if (updatedTaskName.isEmpty() || updatedTaskDescription.isEmpty() || updatedTaskAssignDate.isEmpty() || selectedEstateIndex == 0) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }


        // Update task in database
        model.updateTask(taskId,updatedTaskName,updatedTaskDescription,updatedTaskType,updatedTaskAssignDate,updatedTaskDueDate,selectedEstateIndex)
        Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
        finish() // Close the activity
    }

}



