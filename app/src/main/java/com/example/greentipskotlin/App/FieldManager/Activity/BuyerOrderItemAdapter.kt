package com.example.greentipskotlin.App.FieldManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.databinding.OrderItemCardBinding

class BuyerOrderItemAdapter(private var orderItems:List<OrderItem>):RecyclerView.Adapter<BuyerOrderItemAdapter.orderItemViewHolder>() {

    class orderItemViewHolder(private val binding: OrderItemCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(orderItem: OrderItem){
            binding.tvOrderItemID.text=orderItem.ORDER_ITEM_ID.toString()
            binding.tvOrderItemName.text=orderItem.ORDER_ITEM_NAME
            binding.tvOrderItemPrice.text=orderItem.ORDER_ITEM_PRICE.toString()
            binding.tvOrderItemQuantity.text=orderItem.ORDER_ITEM_QUANTITY.toString()
            binding.tvOrderTotalPrice.text=orderItem.ORDER_ITEM_TOTAL_PRICE.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderItemViewHolder {
        val binding = OrderItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return orderItemViewHolder(binding)
    }

    override fun getItemCount(): Int = orderItems.size

    override fun onBindViewHolder(holder: orderItemViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    fun updateList(newList: List<OrderItem>) {
        orderItems = newList
        notifyDataSetChanged()
    }

}