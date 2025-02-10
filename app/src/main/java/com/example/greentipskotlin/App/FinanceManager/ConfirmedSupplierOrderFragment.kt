package com.example.greentipskotlin.App.FinanceManager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.FieldManager.Activity.BuyerOrderAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.OrderDetails
import com.example.greentipskotlin.App.FieldManager.Activity.SupplierOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierConfirmedOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierOrderDetails
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding
import com.example.greentipskotlin.databinding.FragmentConfirmedSupplierOrderBinding

class ConfirmedSupplierOrderFragment : Fragment() {

    private var _binding:FragmentConfirmedSupplierOrderBinding? = null
    private val binding get() = _binding!!

    private val model:SupplierOrderViewModel by viewModels()

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
        _binding = FragmentConfirmedSupplierOrderBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        supplierConfirmedOrderAdapter = SupplierConfirmedOrderAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), SupplierOrderDetails::class.java).apply {
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

        model.supplierOffersApprovedByCEO.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            supplierConfirmedOrderAdapter.updateList(listToDisplay)
            binding.confirmedSupplierOrderCount.text=listToDisplay.size.toString()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshCEOApprovedOrders()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.supplierOffersApprovedByCEO.value?.let { updatedList ->
            supplierConfirmedOrderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID } else updatedList)
        }
    }

}