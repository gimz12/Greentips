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
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierConfirmedOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierOrderDetails
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierPaymentDetails
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierPendingOrderAdapter
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentConfirmedSupplierOrderBinding
import com.example.greentipskotlin.databinding.FragmentPendingSupllierOrdersBinding

class PendingSupplierOrdersFragment : Fragment() {

    private var _binding: FragmentPendingSupllierOrdersBinding? = null
    private val binding get() = _binding!!

    private val model: SupplierPaymentViewModel by viewModels()

    private lateinit var supplierPendingOrderAdapter: SupplierPendingOrderAdapter

    private var isSorted: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPendingSupllierOrdersBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        supplierPendingOrderAdapter = SupplierPendingOrderAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), SupplierPaymentDetails::class.java).apply {
                putExtra("PAYMENT_ID", selectedOrder.PAYMENT_ID)
                putExtra("PAYMENT_ORDER_ID", selectedOrder.PAYMENT_ORDER_ID)
                putExtra("PAYMENT_USER_ID", selectedOrder.PAYMENT_USER_ID)
                putExtra("PAYMENT_DATE", selectedOrder.PAYMENT_DATE)
                putExtra("PAYMENT_TIME", selectedOrder.PAYMENT_TIME)
                putExtra("PAYMENT_TYPE", selectedOrder.PAYMENT_TYPE)
                putExtra("PAYMENT_STATUS", selectedOrder.PAYMENT_STATUS)
                putExtra("PAID_AMOUNT", selectedOrder.PAID_AMOUNT)
                putExtra("REMAIN_AMOUNT", selectedOrder.REMAIN_AMOUNT)
                putExtra("TOTAL_AMOUNT", selectedOrder.TOTAL_AMOUNT)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.supplierPendingOrderRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.supplierPendingOrderRecyclerView.adapter = supplierPendingOrderAdapter


        model.supplierPendingOrders.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.PAYMENT_DATE } else updateList
            supplierPendingOrderAdapter.updateList(listToDisplay)
            binding.pendingSupplierOrderCount.text=listToDisplay.size.toString()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshPendingSupplierOrders()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.supplierPendingOrders.value?.let { updatedList ->
            supplierPendingOrderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.PAYMENT_DATE } else updatedList)

        }
    }

}