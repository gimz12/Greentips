package com.example.greentipskotlin.App.CEO

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.CEO.Activity.BuyerOrderHistory
import com.example.greentipskotlin.App.FieldManager.Activity.BuyerOrderAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.OrderDetails
import com.example.greentipskotlin.App.FieldManager.Activity.SupplierOrderAdapter
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerOrderHisBinding
import com.example.greentipskotlin.databinding.FragmentSupplierOrderReqBinding

class buyer_Order_HisFragment : Fragment() {

    private var _binding: FragmentBuyerOrderHisBinding? = null
    private val binding get() = _binding!!

    private val model: BuyerOrderViewModel by viewModels()

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
        _binding = FragmentBuyerOrderHisBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        orderAdapter = BuyerOrderAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), BuyerOrderHistory::class.java).apply {
                putExtra("ORDER_ID", selectedOrder.ORDER_ID)
                putExtra("USER_ID", selectedOrder.USER_ID)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.buyerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buyerRecyclerView.adapter = orderAdapter

        model.buyerOrders.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            orderAdapter.updateList(listToDisplay)
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