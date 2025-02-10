package com.example.greentipskotlin.App.Admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Admin.viewModel.IntercropsViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentCeoDashboardBinding
import com.example.greentipskotlin.databinding.FragmentDashboardBinding

class dashboardFragment : Fragment() {


    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val empViewModel: EmployeeViewModel by viewModels()
    private val coconutViewModel: CoconutViewModel by viewModels()
    private val intercropsViewModel: IntercropsViewModel by viewModels()
    private val supplierViewModel: SupplierViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
        val employeeId = sharedPrefs.getInt("employeeId", 0)

        val totalEmployees = empViewModel.getEmployeeCount()
        binding.totalEmpCount.text=totalEmployees.toString()

        val totalCoconuts = coconutViewModel.getCoconutTreeCount()
        binding.totalCoconutTrees.text = totalCoconuts.toString()

        val totalIntercrops = intercropsViewModel.getIntercropCount()
        binding.totalIntercrops.text = totalIntercrops.toString()

        val totalSupplierCount = supplierViewModel.getSupplierCount()
        binding.totalSuppliers.text = totalSupplierCount.toString()


    }








}