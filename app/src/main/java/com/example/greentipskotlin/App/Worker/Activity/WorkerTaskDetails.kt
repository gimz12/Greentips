package com.example.greentipskotlin.App.Worker.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.databinding.ActivityWorkerTaskDetailsBinding

class WorkerTaskDetails : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerTaskDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkerTaskDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val taskId = intent.getStringExtra("TASK_ID")
        val estateId = intent.getStringExtra("TASK_ESTATE_ID_FR")
        val taskName = intent.getStringExtra("Task_NAME")
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION")
        val taskType = intent.getStringExtra("TASK_TYPE")
        val taskAssignDate = intent.getStringExtra("TASK_ASSIGN_DATE")
        val taskDueDate = intent.getStringExtra("TASK_DUE_DATE")
        val taskProgress = intent.getStringExtra("TASK_PROGRESS")
        val taskChallenges = intent.getStringExtra("TASK_CHALLENGES")
        val taskSolution = intent.getStringExtra("TASK_SOLUTION")

        // Set the data to the respective TextViews
        binding.tvTaskId.text = "Task ID: $taskId"
        binding.tvEstateId.text = "Estate Id: $estateId"
        binding.tvTaskName.text = "Task Name: $taskName"
        binding.tvTaskDescription.text = "Task Description: $taskDescription"
        binding.tvTaskType.text = "Task Type: $taskType"
        binding.tvTaskAssignDate.text = "Task Assign Date: $taskAssignDate"
        binding.tvTaskDueDate.text = "Task Due Date: $taskDueDate"
        binding.tvTaskProgress.text = "Task Progress: $taskProgress"
        binding.tvTaskChallenges.text = "Task Challenges: $taskChallenges"
        binding.tvTaskSolutions.text = "Task Solution: $taskSolution"
    }
}
