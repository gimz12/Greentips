package com.example.greentipskotlin.App.Buyer.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.databinding.OrderCardBinding

class OrderAdapter(private var orders:List<BuyerOrder>): RecyclerView.Adapter<OrderAdapter.orderViewHolder>() {

    class orderViewHolder(private val binding: OrderCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(buyerOrder: BuyerOrder){
            binding.tvOrderID.text=buyerOrder.ORDER_ID.toString()
            binding.tvOrderCost.text=buyerOrder.ORDER_COST.toString()
            binding.tvOrderDate.text=buyerOrder.ORDER_DATE
            binding.tvOrderStatus.text=buyerOrder.ORDER_STATUS
            binding.tvOrderShippingAddress.text=buyerOrder.ORDER_SHIPPING_ADDRESS

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orderViewHolder {
        val binding = OrderCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return orderViewHolder(binding)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: orderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    fun updateList(newList: List<BuyerOrder>) {
        orders = newList
        notifyDataSetChanged()
    }

}