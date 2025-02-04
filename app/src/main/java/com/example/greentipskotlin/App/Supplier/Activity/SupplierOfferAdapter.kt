package com.example.greentipskotlin.App.Supplier.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.databinding.SupplierOfferViewCardBinding

class SupplierOfferAdapter(
    private var confirmedSupplierOrders:List<SupplierOrder>,
    private val onItemClick: (SupplierOrder) -> Unit) :
    RecyclerView.Adapter<SupplierOfferAdapter.orderViewHolder>() {

    class orderViewHolder(private val binding: SupplierOfferViewCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(supplierOrder: SupplierOrder, onItemClick: (SupplierOrder) -> Unit) {
            // Set basic information
            binding.tvSupplierOrderId.text = supplierOrder.ORDER_ID.toString()
            binding.tvSupplierID.text = supplierOrder.USER_ID.toString()
            binding.tvOrderItem.text = supplierOrder.ITEM_NAME
            binding.tvOrderItemQuantity.text = supplierOrder.ITEM_QUANTITY.toString()
            binding.tvSupplierEstimateDeliveryDate.text = supplierOrder.ESTIMATE_DELIVERY_DATE
            binding.tvQuantityPrice.text = supplierOrder.QUANTITY_PRICE.toString()
            binding.tvOrderTotalPrice.text = supplierOrder.TOTAL_AMOUNT.toString()

            // Get payment status from the database

            // Determine status
            binding.tvStatus.text = when {
                supplierOrder.FIELDMANAGER_STATUS == "Pending" && supplierOrder.CEO_STATUS == "Pending" -> "Pending"
                supplierOrder.FIELDMANAGER_STATUS == "Approved" && supplierOrder.CEO_STATUS == "Pending" -> "Pending"
                supplierOrder.FIELDMANAGER_STATUS == "Approved" && supplierOrder.CEO_STATUS == "Approved" -> "Approved"
                supplierOrder.FIELDMANAGER_STATUS == "Declined" || supplierOrder.CEO_STATUS == "Declined" -> "Declined"
                else -> "Unknown"
            }

            // Set click listener
            binding.root.setOnClickListener {
                onItemClick(supplierOrder)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderViewHolder {
        val binding = SupplierOfferViewCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return orderViewHolder(binding)
    }

    override fun getItemCount(): Int = confirmedSupplierOrders.size

    override fun onBindViewHolder(holder: orderViewHolder, position: Int) {
        holder.bind(confirmedSupplierOrders[position], onItemClick)
    }

    fun updateList(newList: List<SupplierOrder>) {
        confirmedSupplierOrders = newList
        notifyDataSetChanged()
    }
}