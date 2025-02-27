package com.example.greentipskotlin.App.Worker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.App.CEO.Activity.UpdateTask
import com.example.greentipskotlin.App.FieldManager.Activity.TaskAdapter
import com.example.greentipskotlin.App.Worker.Activity.WorkerTaskDetails
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentTaskHistoryBinding
import com.example.greentipskotlin.databinding.FragmentViewAllPendingTasksBinding


class viewAllPendingTasksFragment : Fragment() {

    private var _binding: FragmentViewAllPendingTasksBinding? = null
    private val binding get() = _binding!!

    private val model: TaskViewModel by viewModels()
    private lateinit var taskAdapter: TaskAdapter

    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewAllPendingTasksBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        taskAdapter = TaskAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), WorkerTaskDetails::class.java).apply {
                putExtra("TASK_ID", selectedOrder.TASK_ID)
                putExtra("TASK_ESTATE_ID_FR", selectedOrder.TASK_ESTATE_ID_FR)
                putExtra("Task_NAME", selectedOrder.TASK_NAME)
                putExtra("TASK_DESCRIPTION", selectedOrder.TASK_DESCRIPTION)
                putExtra("TASK_TYPE", selectedOrder.TASK_TYPE)
                putExtra("TASK_ASSIGN_DATE", selectedOrder.TASK_ASSIGN_DATE)
                putExtra("TASK_DUE_DATE", selectedOrder.TASK_DUE_DATE)
                putExtra("TASK_PROGRESS", selectedOrder.TASK_PROGRESS)
                putExtra("TASK_CHALLENGES", selectedOrder.TASK_CHALLENGES)
                putExtra("TASK_SOLUTION", selectedOrder.TASK_SOLUTION)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.taskRecyclerView.adapter = taskAdapter
        binding.taskCounter.text=taskAdapter.itemCount.toString()

        model.tasksByEmpId.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.TASK_NAME } else updateList
            taskAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = requireActivity().getSharedPreferences("LoggedUser", MODE_PRIVATE)
        val employeeId = sharedPref.getInt("employeeId", -1) // Default to -1 if not found

        if (employeeId != -1) {
            model.refreshTaskByEmpId(employeeId)
        } else {
            Toast.makeText(requireContext(), "Employee ID not found", Toast.LENGTH_SHORT).show()
        }
    }


    private fun toggleSort() {
        isSorted = !isSorted
        model.tasksByEmpId.value?.let { updatedList ->
            taskAdapter.updateList(if (isSorted) updatedList.sortedBy { it.TASK_NAME }else updatedList)
        }
    }

}