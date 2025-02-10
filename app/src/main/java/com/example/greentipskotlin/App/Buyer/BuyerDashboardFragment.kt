package com.example.greentipskotlin.App.Buyer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerDashboardBinding

class BuyerDashboardFragment : Fragment() {

    // View Binding property
    private var _binding: FragmentBuyerDashboardBinding? = null
    private val binding get() = _binding!!

    private val model: BuyerOrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using view binding
        _binding = FragmentBuyerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val sharedPrefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPrefs.getInt("USER_ID", 0)




        val pendingOrdersCount = model.getPendingOrdersCount(userId)

        // Update the pending orders TextView with the retrieved count
        binding.pendingOrdersText.text = "You Have $pendingOrdersCount Pending Orders"

        // Setup click listeners for navigation buttons
        binding.viewPendingOrdersButton.setOnClickListener {
            val orderHistoryFragment = BuyerOrderHistoryFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, orderHistoryFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.viewCoconutsButton.setOnClickListener {
            val catalogFragment = BuyerCatalogueFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, catalogFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.viewIntercropsButton.setOnClickListener {
            val catalogFragment = BuyerCatalogueFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, catalogFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
