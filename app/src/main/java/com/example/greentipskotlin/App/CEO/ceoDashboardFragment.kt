package com.example.greentipskotlin.App.CEO

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentCeoDashboardBinding
import com.example.greentipskotlin.databinding.FragmentFieldManagerDashboardBinding


class ceoDashboardFragment : Fragment() {

    private var _binding: FragmentCeoDashboardBinding? = null
    private val binding get() = _binding!!

    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCeoDashboardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPrefs = requireActivity().getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
        val employeeId = sharedPrefs.getInt("employeeId", 0)

        val monthlyOrders = buyerOrderViewModel.getNonCancelledBuyerOrderCount()
        binding.monthlyOrders.text=monthlyOrders.toString()


        val monthlySales = buyerOrderViewModel.getSumOfNonCancelledBuyerOrderCost()
        binding.monthlySales.text = monthlySales.toString()


    }
}
