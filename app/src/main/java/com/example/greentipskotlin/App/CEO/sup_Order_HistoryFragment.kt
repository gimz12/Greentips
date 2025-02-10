package com.example.greentipskotlin.App.CEO

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.CEO.Activity.SupplierOrderHistory
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierConfirmedOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierOrderDetails
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentConfirmedSupplierOrderBinding
import com.example.greentipskotlin.databinding.FragmentSupOrderHistoryBinding

class sup_Order_HistoryFragment : Fragment() {

    private var _binding: FragmentSupOrderHistoryBinding? = null
    private val binding get() = _binding!!

    private val model: SupplierOrderViewModel by viewModels()

    private lateinit var supplierConfirmedOrderAdapter: SupplierConfirmedOrderAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSupOrderHistoryBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        supplierConfirmedOrderAdapter = SupplierConfirmedOrderAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), SupplierOrderHistory::class.java).apply {
                putExtra("ORDER_ID", selectedOrder.ORDER_ID)
                putExtra("USER_ID", selectedOrder.USER_ID)
                putExtra("ITEM_NAME", selectedOrder.ITEM_NAME)
                putExtra("ITEM_QUANTITY", selectedOrder.ITEM_QUANTITY)
                putExtra("ESTIMATE_DELIVERY_DATE", selectedOrder.ESTIMATE_DELIVERY_DATE)
                putExtra("QUANTITY_PRICE", selectedOrder.QUANTITY_PRICE)
                putExtra("TOTAL_AMOUNT", selectedOrder.TOTAL_AMOUNT)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.supplierOfferRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.supplierOfferRecyclerView.adapter = supplierConfirmedOrderAdapter

        model.supplierApprovedOrders.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            supplierConfirmedOrderAdapter.updateList(listToDisplay)
            binding.supplierOrderHistory.text=listToDisplay.size.toString()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshApprovedOrders()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.supplierApprovedOrders.value?.let { updatedList ->
            supplierConfirmedOrderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID } else updatedList)
        }
    }


}