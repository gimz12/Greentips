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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using DataBindingUtil
        _binding = FragmentEmpMngBinding.inflate(inflater, container, false)

        val addEmployeeImage = binding.insertEmployeeImageView

        addEmployeeImage.setOnClickListener {
            onClickAddNewEmployee()
        }

        // Set onClickListener to trigger the method when the image is clicked
        addEmployeeImage.setOnClickListener {
            onClickAddNewEmployee()
        }

        employeeAdapter = EmployeeAdapter(model.getAllEmployees()) // Modify getAllCities to return the list of cities
        binding.employeeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.employeeRecyclerView.adapter = employeeAdapter



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onClickAddNewEmployee() {
        val groupMessageIntent = Intent(requireContext(), EmployeeInsert::class.java)
        startActivity(groupMessageIntent)  // Start the EmployeeInsert activity
    }

}