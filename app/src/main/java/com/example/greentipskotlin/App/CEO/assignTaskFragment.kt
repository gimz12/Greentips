package com.example.greentipskotlin.App.CEO

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.App.FieldManager.Activity.SupplierOrderAdapter
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentAssignTaskBinding
import com.example.greentipskotlin.databinding.FragmentSupplierOrderReqBinding

class assignTaskFragment : Fragment() {

    private var _binding: FragmentAssignTaskBinding? = null
    private val binding get() = _binding!!

    private val model: TaskViewModel by viewModels()
    private val empModel: EmployeeViewModel by viewModels()


    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAssignTaskBinding.inflate(inflater, container, false)

        val estateNames = mutableListOf("Select a Estate")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        estateNames.addAll(empModel.getAllEstateNames())
        binding.estateSpinner.adapter = adapter

        binding.insertTask.setOnClickListener {
            // Get the values from the EditText fields
            val taskName = binding.teTaskName.text.toString()
            val taskType = binding.teTaskType.text.toString()
            val assignTask = binding.teAssignTask.text.toString()
            val dueDate = binding.teDueDate.text.toString()
            val taskDescription = binding.teTaskDescription.text.toString()

            // Check if all fields are filled
            if (taskName.isNotEmpty() && taskType.isNotEmpty() && assignTask.isNotEmpty() && dueDate.isNotEmpty() && taskDescription.isNotEmpty()) {
                // Create a new Task object
                val task = Task(
                    TASK_ID = null,
                    TASK_ESTATE_ID_FR = binding.estateSpinner.selectedItemId.toInt(),
                    TASK_NAME = taskName,
                    TASK_DESCRIPTION = taskDescription,
                    TASK_TYPE = taskType,
                    TASK_ASSIGN_DATE = assignTask,
                    TASK_PROGRESS = "Not Started",
                    TASK_DUE_DATE = dueDate,
                    TASK_CHALLENGES = "",
                    TASK_SOLUTION = ""
                )

                // Call insertTask to add the task
                model.insertTask(task)

                Toast.makeText(
                    requireContext(),
                    "Task Created",
                    Toast.LENGTH_SHORT
                ).show()
                resetFields()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter all the data, and ensure its valid.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

    // Reset the input fields after adding the task
    private fun resetFields() {
        binding.teTaskName.text.clear()
        binding.teTaskType.text.clear()
        binding.teAssignTask.text.clear()
        binding.teDueDate.text.clear()
        binding.teTaskDescription.text.clear()
        binding.estateSpinner.setSelection(0) // Reset spinner to default
    }

}