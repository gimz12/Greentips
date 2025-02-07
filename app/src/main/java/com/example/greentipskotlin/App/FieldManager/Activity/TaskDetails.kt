package com.example.greentipskotlin.App.FieldManager.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.databinding.ActivityTaskDetailsBinding
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding

class TaskDetails : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailsBinding
    private val model: TaskViewModel by viewModels()

    private var selectedWorkerId: Int? = null
    private var taskId: Int = -1
    private var estateId: Int = -1
    private lateinit var assignedWorkersAdapter: ArrayAdapter<String>
    private val assignedWorkersList = mutableListOf<String>()
    private var taskProgress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from Intent
        taskId = intent.getIntExtra("TASK_ID", -1)
        estateId = intent.getIntExtra("TASK_ESTATE_ID_FR", -1)
        val taskName = intent.getStringExtra("Task_NAME") ?: "N/A"
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION") ?: "N/A"
        val taskType = intent.getStringExtra("TASK_TYPE") ?: "N/A"
        val taskAssignDate = intent.getStringExtra("TASK_ASSIGN_DATE") ?: "N/A"
        val taskDueDate = intent.getStringExtra("TASK_DUE_DATE") ?: "N/A"
        taskProgress = intent.getStringExtra("TASK_PROGRESS") ?: "N/A"
        val taskChallenges = intent.getStringExtra("TASK_CHALLENGES") ?: "N/A"
        val taskSolution = intent.getStringExtra("TASK_SOLUTION") ?: "N/A"

        // Populate UI
        binding.tvTaskId.text = "Task ID: $taskId"
        binding.tvEstateId.text = "Estate ID: $estateId"
        binding.tvTaskName.text = "Task Name: $taskName"
        binding.tvTaskDescription.text = "Task Description: $taskDescription"
        binding.tvTaskType.text = "Task Type: $taskType"
        binding.tvTaskAssignDate.text = "Task Assign Date: $taskAssignDate"
        binding.tvTaskDueDate.text = "Task Due Date: $taskDueDate"
        binding.tvTaskProgress.text = "Task Progress: $taskProgress"
        binding.tvTaskChallenges.text = "Task Challenges: $taskChallenges"
        binding.tvTaskSolutions.text = "Task Solution: $taskSolution"

        // Set up Assigned Workers List Adapter
        assignedWorkersAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, assignedWorkersList)
        binding.lvAssignedWorkers.adapter = assignedWorkersAdapter

        loadWorkers()

        // Load assigned workers
        loadAssignedWorkers()

        // Assign worker to task
        binding.btnAssignWorker.setOnClickListener {
            if (taskProgress != "Task Complete") {
                assignWorkerToTask()
            } else {
                Toast.makeText(this, "Task is completed, cannot assign new workers", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnReportChallenges.setOnClickListener {
            if (taskProgress != "Task Complete") {
                val newChallenge = binding.teChallenges.text.toString()

                if (newChallenge.isNotEmpty()) {
                    model.updateTaskChallenges(taskId, newChallenge)
                    binding.tvTaskChallenges.text = "Task Challenges: $newChallenge"
                    Toast.makeText(this, "Challenge Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter a challenge", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Task is completed, cannot report challenges", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUpdateProgress.setOnClickListener {
            if (taskProgress != "Task Complete") {
                val newProgress = binding.teTaskProgress.text.toString()

                if (newProgress.isNotEmpty()) {
                    model.updateTaskProgress(taskId, newProgress)
                    binding.tvTaskProgress.text = "Task Progress: $newProgress"
                    Toast.makeText(this, "Progress Updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter Progress", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Task is completed, cannot update progress", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnFinishProgress.setOnClickListener {
            showTaskCompletionConfirmationDialog(taskId)
        }

        // Remove assigned worker
        binding.lvAssignedWorkers.setOnItemClickListener { _, _, position, _ ->
            val workerId = assignedWorkersList[position].split(" - ")[0].toInt()
            if (taskProgress != "Task Complete") {
                removeWorkerFromTask(workerId)
            } else {
                Toast.makeText(this, "Task is completed, cannot remove workers", Toast.LENGTH_SHORT).show()
            }
        }

        // Spinner Item Selected Listener to capture selected worker
        binding.spinnerWorkers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedWorkerId = binding.spinnerWorkers.selectedItem.toString().split(" - ")[0].toInt()
                Log.d("TaskDetails", "Selected Worker ID: $selectedWorkerId")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedWorkerId = null
            }
        }
    }

    private fun loadWorkers() {
        model.getAllWorkersByEstate(estateId).observe(this, { workers ->
            if (workers != null) {
                // Log the retrieved workers
                Log.d("TaskDetails", "Workers retrieved: $workers")

                val workerNames = workers.map { "${it.EmployerId} - ${it.Estate_ID}" }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, workerNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerWorkers.adapter = adapter
            } else {
                Toast.makeText(this, "No workers available", Toast.LENGTH_SHORT).show()
                Log.d("TaskDetails", "No workers found for estateId: $estateId")
            }
        })
    }

    private fun loadAssignedWorkers() {
        val assignedWorkers = model.getWorkersByTaskId(taskId)
        Log.d("TaskDetails", "Assigned workers for taskId $taskId: $assignedWorkers")

        assignedWorkersList.clear()
        assignedWorkers.forEach { workerId ->
            val workerName = model.getEmployeeNameById(workerId)
            assignedWorkersList.add("$workerId - $workerName")
        }
        assignedWorkersAdapter.notifyDataSetChanged()
        Log.d("TaskDetails", "Updated assigned workers list: $assignedWorkersList")
    }

    private fun assignWorkerToTask() {
        selectedWorkerId?.let { workerId ->
            if (!assignedWorkersList.any { it.startsWith("$workerId -") }) {
                model.assignTaskToWorker(taskId, workerId)
                loadAssignedWorkers()
                Toast.makeText(this, "Worker assigned successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Worker already assigned!", Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, "Please select a worker!", Toast.LENGTH_SHORT).show()
    }

    private fun removeWorkerFromTask(workerId: Int) {
        model.removeWorkerFromTask(taskId, workerId)
        loadAssignedWorkers()
        Toast.makeText(this, "Worker removed from task!", Toast.LENGTH_SHORT).show()
    }

    private fun showTaskCompletionConfirmationDialog(taskId: Int) {
        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
            .setPositiveButton("Yes") { _, _ ->
                val newProgress = "Task Complete"
                if (newProgress.isNotEmpty()) {
                    model.updateTaskProgress(taskId, newProgress)
                    binding.tvTaskProgress.text = "Task Progress: $newProgress"
                    Toast.makeText(this, "Task Completed", Toast.LENGTH_SHORT).show()
                    taskProgress = newProgress

                    // Disable buttons after task completion
                    disableTaskModificationButtons()
                } else {
                    Toast.makeText(this, "Task Could Not Complete", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()

        // Set title and message dynamically if needed
        dialogBinding.dialogTitle.text = "Complete Task"
        dialogBinding.dialogMessage.text = "Are you sure you want to mark this task as complete?"
    }

    private fun disableTaskModificationButtons() {
        binding.btnAssignWorker.isEnabled = false
        binding.btnReportChallenges.isEnabled = false
        binding.btnUpdateProgress.isEnabled = false
        binding.lvAssignedWorkers.isEnabled = false
        binding.spinnerWorkers.isEnabled = false
    }
}
