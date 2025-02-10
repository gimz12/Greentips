package com.example.greentipskotlin.App.Supplier

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.FieldManager.Activity.SupplierOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierConfirmedOrderAdapter
import com.example.greentipskotlin.App.FinanceManager.Activity.SupplierOrderDetails
import com.example.greentipskotlin.App.Supplier.Activity.SupplierOfferAdapter
import com.example.greentipskotlin.App.Supplier.Activity.UpdateSupplierOffer
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentSupplierOfferManagementBinding
import com.example.greentipskotlin.databinding.FragmentSupplierViewSupplyDetailsBinding

class supplierViewSupplyDetailsFragment : Fragment() {

    private var _binding: FragmentSupplierViewSupplyDetailsBinding? = null
    private val binding get() = _binding!!

    private val model: SupplierOrderViewModel by viewModels()

    private lateinit var supplierOfferAdapter: SupplierOfferAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSupplierViewSupplyDetailsBinding.inflate(inflater, container, false)

        val sortButton = binding.sortButton

        sortButton.setOnClickListener(){
            toggleSort()
        }

        supplierOfferAdapter = SupplierOfferAdapter(emptyList()) { selectedOrder ->
            val intent = Intent(requireContext(), UpdateSupplierOffer::class.java).apply {
                putExtra("ORDER_ID", selectedOrder.ORDER_ID)
                putExtra("USER_ID", selectedOrder.USER_ID)
                putExtra("ITEM_NAME", selectedOrder.ITEM_NAME)
                putExtra("ITEM_DESCRIPTION", selectedOrder.ITEM_DESCRIPTION)
                putExtra("ITEM_QUANTITY", selectedOrder.ITEM_QUANTITY)
                putExtra("ESTIMATE_DELIVERY_DATE", selectedOrder.ESTIMATE_DELIVERY_DATE)
                putExtra("QUANTITY_PRICE", selectedOrder.QUANTITY_PRICE)
                putExtra("TOTAL_AMOUNT", selectedOrder.TOTAL_AMOUNT)
                putExtra("FIELD_MANAGER_STATUS", selectedOrder.FIELDMANAGER_STATUS)
                putExtra("CEO_STATUS", selectedOrder.CEO_STATUS)
            }
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.supplierOfferRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.supplierOfferRecyclerView.adapter = supplierOfferAdapter

        model.supplierOffersByUserId.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            supplierOfferAdapter.updateList(listToDisplay)
            binding.orderCount.text=listToDisplay.size.toString()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", 0)
        model.getSupplierOrdersByUserId(userId)
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.supplierOffersByUserId.value?.let { updatedList ->
            supplierOfferAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID } else updatedList)
        }
    }

}