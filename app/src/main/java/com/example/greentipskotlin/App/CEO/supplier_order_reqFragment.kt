package com.example.greentipskotlin.App.CEO

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.FieldManager.Activity.SupplierOrderAdapter
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding
import com.example.greentipskotlin.databinding.FragmentSupplierOfferManagementBinding
import com.example.greentipskotlin.databinding.FragmentSupplierOrderReqBinding

class supplier_order_reqFragment : Fragment() {

    private var _binding:FragmentSupplierOrderReqBinding? = null
    private val binding get() = _binding!!

    private val model: SupplierOrderViewModel by viewModels()

    private lateinit var supplierOrderAdapter: SupplierOrderAdapter

    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSupplierOrderReqBinding.inflate(inflater, container, false)

        supplierOrderAdapter = SupplierOrderAdapter(emptyList()) { supplierOrder, newStatus ->
            showConfirmationDialog(supplierOrder, newStatus)
        }

        binding.supplierOfferRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.supplierOfferRecyclerView.adapter = supplierOrderAdapter

        model.supplierOffersApprovedByFieldManager.observe(viewLifecycleOwner) { updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.ORDER_ID } else updateList
            supplierOrderAdapter.updateList(listToDisplay)
        }

        binding.sortButton.setOnClickListener {
            toggleSort()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshFieldManagerApprovedOrders()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.supplierOffersApprovedByFieldManager.value?.let { updatedList ->
            supplierOrderAdapter.updateList(if (isSorted) updatedList.sortedBy { it.ORDER_ID } else updatedList)
        }
    }

    private fun showConfirmationDialog(supplierOrder: SupplierOrder, newStatus: String) {

        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)

        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())  // Fix context for fragment
        builder.setView(dialogBinding.root)
            .setPositiveButton("Yes") { _, _ ->
                if (newStatus == "Approved") {
                    model.updateCeoStatus(supplierOrder.ORDER_ID, "Approved")
                } else {
                    model.updateCeoStatus(supplierOrder.ORDER_ID, "Declined")
                }
            }
            .setNegativeButton("No", null)
            .show()

        dialogBinding.dialogTitle.text = "Order Status Update"  // Title for the dialog

        // Set different messages based on the status
        val message = if (newStatus == "Approved") {
            "Are you sure you want to approve this order?"
        } else {
            "Are you sure you want to decline this order?"
        }
        dialogBinding.dialogMessage.text = message
    }

}