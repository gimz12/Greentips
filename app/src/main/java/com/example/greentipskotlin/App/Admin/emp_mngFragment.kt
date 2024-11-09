package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.EmployeeAdapter
import com.example.greentipskotlin.App.Admin.Activity.EmployeeInsert
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentEmpMngBinding

class emp_mngFragment : Fragment() {

    private var _binding: FragmentEmpMngBinding? = null
    private val binding get() = _binding!!

    private lateinit var employeeAdapter: EmployeeAdapter

    // ViewModel initialization
    private val model: EmployeeViewModel by viewModels()

    //creating a variable to know if the recycler view is sorted or not
    private var isSorted: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using DataBindingUtil
        _binding = FragmentEmpMngBinding.inflate(inflater, container, false)

        //creating Instance of Layout Components
        val addEmployeeImage = binding.insertEmployeeImageView
        val sortButton= binding.sortButton

        //Initialize Recycler View
        employeeAdapter = EmployeeAdapter(emptyList()) // Modify getAllCities to return the list of cities
        binding.employeeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.employeeRecyclerView.adapter = employeeAdapter

        //Listener for Add New Employee
        addEmployeeImage.setOnClickListener {
            onClickAddNewEmployee()
        }

        //Listener for Sort
        sortButton.setOnClickListener {
            toggleSort()
        }

        model.employees.observe(viewLifecycleOwner) { updatedList ->
            val listToDisplay = if (isSorted) updatedList.sortedBy { it.employeeName } else updatedList
            employeeAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Intent to AddNewEmployee Activity
    private fun onClickAddNewEmployee() {
        val groupMessageIntent = Intent(requireContext(), EmployeeInsert::class.java)
        startActivity(groupMessageIntent)  // Start the EmployeeInsert activity
    }

    //Method for SortEmployee using their Name
    private fun toggleSort() {
        isSorted = !isSorted
        model.employees.value?.let { updatedList ->
            employeeAdapter.updateList(if (isSorted) updatedList.sortedBy { it.employeeName } else updatedList)
        }
    }


}