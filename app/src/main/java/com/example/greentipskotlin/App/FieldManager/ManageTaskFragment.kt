package com.example.greentipskotlin.App.FieldManager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.App.FieldManager.Activity.BuyerOrderAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.OrderDetails
import com.example.greentipskotlin.App.FieldManager.Activity.TaskAdapter
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentFieldManagerManageBuyerOrderBinding
import com.example.greentipskotlin.databinding.FragmentManageTaskBinding

class ManageTaskFragment : Fragment() {

    private var _binding: FragmentManageTaskBinding? = null
    private val binding get() =_binding!!

    private val model: TaskViewModel by viewModels()
    private val empModel: EmployeeViewModel by viewModels()

    private lateinit var taskAdapter: TaskAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentManageTaskBinding.inflate(inflater,container,false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        taskAdapter = TaskAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), OrderDetails::class.java).apply {
                putExtra("TASK_ID", selectedOrder.TASK_ID)
                putExtra("Task_NAME", selectedOrder.TASK_NAME)
                putExtra("TASK_DESCRIPTION", selectedOrder.TASK_DESCRIPTION)
                putExtra("TASK_TYPE", selectedOrder.TASK_TYPE)
                putExtra("TASK_ASSIGN_DATE", selectedOrder.TASK_ASSIGN_DATE)
                putExtra("TASK_DUE_DATE", selectedOrder.TASK_DUE_DATE)
                putExtra("TASK_PROGRESS", selectedOrder.TASK_PROGRESS)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.taskRecyclerView.adapter = taskAdapter

        model.tasksByEstateId.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.TASK_NAME } else updateList
            taskAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = requireActivity().getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
        val empId = sharedPref.getInt("employeeId", -1) // Default value -1 if not found

        if (empId != -1) {
            val estateId = empModel.getEstateIdByEmployeeId(empId)
            model.getTasksByEstateId(estateId)
        } else {
            Log.e("AssignTaskFragment", "Employee ID not found in SharedPreferences")
        }
    }


    private fun toggleSort() {
        isSorted = !isSorted
        model.tasksByEstateId.value?.let { updatedList ->
            taskAdapter.updateList(if (isSorted) updatedList.sortedBy { it.TASK_NAME }else updatedList)
        }
    }

}