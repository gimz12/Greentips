package com.example.greentipskotlin.App.FinanceManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.SupplierPayment
import com.example.greentipskotlin.databinding.SupplierConfirmedOrderCardBinding
import com.example.greentipskotlin.databinding.SupplierPendingOrderCardBinding

class SupplierPendingOrderAdapter(
    private var pendingSupplierOrders:List<SupplierPayment>,
    private val onItemClick: (SupplierPayment) -> Unit) :RecyclerView.Adapter<SupplierPendingOrderAdapter.orderViewHolder>(){

    class orderViewHolder(private val binding: SupplierPendingOrderCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(supplierPayment: SupplierPayment, onItemClick: (SupplierPayment) -> Unit){
            binding.tvSupplierPaymentId.text=supplierPayment.PAYMENT_ID.toString()
            binding.tvSupplierOrderID.text=supplierPayment.PAYMENT_ORDER_ID.toString()
            binding.tvSupplierID.text=supplierPayment.PAYMENT_USER_ID.toString()
            binding.tvPaymentDate.text=supplierPayment.PAYMENT_DATE
            binding.tvPaymentTime.text=supplierPayment.PAYMENT_TIME
            binding.tvPaymentType.text=supplierPayment.PAYMENT_TYPE
            binding.tvPaymentStatus.text=supplierPayment.PAYMENT_STATUS
            binding.tvPaidAmount.text=supplierPayment.PAID_AMOUNT.toString()
            binding.tvRemainAmount.text=supplierPayment.REMAIN_AMOUNT.toString()
            binding.tvTotalAmount.text=supplierPayment.TOTAL_AMOUNT.toString()

            binding.root.setOnClickListener {
                onItemClick(supplierPayment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderViewHolder {
        val binding = SupplierPendingOrderCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return orderViewHolder(binding)
    }

    override fun getItemCount(): Int = pendingSupplierOrders.size

    override fun onBindViewHolder(holder: orderViewHolder, position: Int) {
        holder.bind(pendingSupplierOrders[position], onItemClick)
    }

    fun updateList(newList: List<SupplierPayment>) {
        pendingSupplierOrders = newList
        notifyDataSetChanged()
    }
}