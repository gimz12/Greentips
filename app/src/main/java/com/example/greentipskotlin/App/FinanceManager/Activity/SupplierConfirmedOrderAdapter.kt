package com.example.greentipskotlin.App.FinanceManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.databinding.SupplierConfirmedOrderCardBinding

class SupplierConfirmedOrderAdapter(
    private var confirmedSupplierOrders:List<SupplierOrder>,
    private val onItemClick: (SupplierOrder) -> Unit) :RecyclerView.Adapter<SupplierConfirmedOrderAdapter.orderViewHolder>(){

        class orderViewHolder(private val binding:SupplierConfirmedOrderCardBinding):RecyclerView.ViewHolder(binding.root){
            fun bind(supplierOrder: SupplierOrder,onItemClick: (SupplierOrder) -> Unit){
                binding.tvSupplierOrderId.text=supplierOrder.ORDER_ID.toString()
                binding.tvSupplierID.text=supplierOrder.USER_ID.toString()
                binding.tvOrderItem.text=supplierOrder.ITEM_NAME
                binding.tvOrderItemQuantity.text=supplierOrder.ITEM_QUANTITY.toString()
                binding.tvSupplierEstimateDeliveryDate.text=supplierOrder.ESTIMATE_DELIVERY_DATE
                binding.tvQuantityPrice.text=supplierOrder.QUANTITY_PRICE.toString()
                binding.tvOrderTotalPrice.text=supplierOrder.TOTAL_AMOUNT.toString()

                binding.root.setOnClickListener {
                    onItemClick(supplierOrder)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderViewHolder {
        val binding = SupplierConfirmedOrderCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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