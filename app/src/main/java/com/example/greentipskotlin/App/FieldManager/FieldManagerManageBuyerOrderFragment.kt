package com.example.greentipskotlin.App.FieldManager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Buyer.Activity.OrderAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.BuyerOrderAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.OrderDetails
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerOrderHistoryBinding
import com.example.greentipskotlin.databinding.FragmentFieldManagerManageBuyerOrderBinding

class FieldManagerManageBuyerOrderFragment : Fragment() {

    private var _binding:FragmentFieldManagerManageBuyerOrderBinding? = null
    private val binding get() =_binding!!

    private val model:BuyerOrderViewModel by viewModels()

    private lateinit var orderAdapter: BuyerOrderAdapter

    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentFieldManagerManageBuyerOrderBinding.inflate(inflater,container,false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        orderAdapter = BuyerOrderAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), OrderDetails::class.java).apply {
                putExtra("ORDER_ID", selectedOrder.ORDER_ID)
                putExtra("ORDER_COST", selectedOrder.ORDER_COST)
                putExtra("ORDER_DATE", selectedOrder.ORDER_DATE)
                putExtra("ORDER_STATUS", selectedOrder.ORDER_STATUS)
                putExtra("ORDER_SHIPPING_ADDRESS", selectedOrder.ORDER_SHIPPING_ADDRESS) // Add image as a URI or resource ID
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.buyerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buyerRecyclerView.adapter = orderAdapter

        model.buyerOrders.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            orderAdapter.updateList(listToDisplay)
            binding.orderCount.text=listToDisplay.size.toString()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshAllOrders()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.buyerOrders.value?.let { updatedList ->
            orderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID }else updatedList)
        }
    }

}