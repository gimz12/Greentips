package com.example.greentipskotlin.App.FieldManager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.FieldManagerViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.App.Model.FieldManagerDataProvider
import com.example.greentipskotlin.databinding.FragmentFieldManagerDashboardBinding


class FieldManagerDashboard : Fragment() {

    // View Binding property
    private var _binding: FragmentFieldManagerDashboardBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()
    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()
    private val supplierOrderViewModel: SupplierOrderViewModel by viewModels()
    private val fieldManagerViewModel: FieldManagerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentFieldManagerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPrefs = requireActivity().getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
        val employeeId = sharedPrefs.getInt("employeeId", 0)

        val estateId = fieldManagerViewModel.getEstateIdByEmployeeId(employeeId)


        val pendingTasksCount = taskViewModel.getPendingTaskCount(estateId)
        binding.pendingTasksCount.text = pendingTasksCount.toString()


        val pendingBuyerOrdersCount = buyerOrderViewModel.getNotDeliveredOrCancelledBuyerOrdersCount()
        binding.pendingBuyerOrdersCount.text = pendingBuyerOrdersCount.toString()


        val pendingSupplierOffersCount = supplierOrderViewModel.getPendingSupplierOrdersCount()
        binding.pendingSupplierOffersCount.text = pendingSupplierOffersCount.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
