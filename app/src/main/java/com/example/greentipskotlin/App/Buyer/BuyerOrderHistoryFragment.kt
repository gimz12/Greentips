package com.example.greentipskotlin.App.Buyer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.BuyerAdapter
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Buyer.Activity.OrderAdapter
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerMngBinding
import com.example.greentipskotlin.databinding.FragmentBuyerOrderHistoryBinding

class BuyerOrderHistoryFragment : Fragment() {

    private var _binding: FragmentBuyerOrderHistoryBinding? = null
    private val binding get() = _binding!!

    private val model:BuyerOrderViewModel by viewModels()

    private lateinit var orderAdapter: OrderAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding=FragmentBuyerOrderHistoryBinding.inflate(inflater,container,false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        orderAdapter= OrderAdapter(emptyList())
        binding.buyerRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.buyerRecyclerView.adapter= orderAdapter

        model.buyerOrders.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            orderAdapter.updateList(listToDisplay)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        model.refreshData(userId) // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.buyerOrders.value?.let { updatedList ->
            orderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID }else updatedList)
        }
    }

}