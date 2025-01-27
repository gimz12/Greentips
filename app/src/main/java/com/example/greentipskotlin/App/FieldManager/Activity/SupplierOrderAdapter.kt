package com.example.greentipskotlin.App.FieldManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.databinding.SupplierOrderCardBinding

class SupplierOrderAdapter(
    private var supplierOrders: List<SupplierOrder>,
    private val onStatusChange: (SupplierOrder, String) -> Unit
) : RecyclerView.Adapter<SupplierOrderAdapter.SupplierViewHolder>() {

    class SupplierViewHolder(private val binding: SupplierOrderCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(supplierOrder: SupplierOrder, onStatusChange: (SupplierOrder, String) -> Unit) {
            binding.tvSupplierOrderId.text = supplierOrder.ORDER_ID.toString()
            binding.tvSupplierID.text = supplierOrder.USER_ID.toString()
            binding.tvOrderItem.text = supplierOrder.ITEM_NAME
            binding.tvOrderItemQuantity.text = supplierOrder.ITEM_QUANTITY.toString()
            binding.tvSupplierEstimateDeliveryDate.text = supplierOrder.ESTIMATE_DELIVERY_DATE
            binding.tvQuantityPrice.text = supplierOrder.QUANTITY_PRICE.toString()
            binding.tvOrderTotalPrice.text = supplierOrder.TOTAL_AMOUNT.toString()

            binding.btnApprove.setOnClickListener {
                onStatusChange(supplierOrder, "Approved")
            }

            binding.btnDecline.setOnClickListener {
                onStatusChange(supplierOrder, "Declined")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val binding = SupplierOrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SupplierViewHolder(binding)
    }

    override fun getItemCount(): Int = supplierOrders.size

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        holder.bind(supplierOrders[position], onStatusChange)
    }

    fun updateList(newList: List<SupplierOrder>) {
        supplierOrders = newList
        notifyDataSetChanged()
    }
}
