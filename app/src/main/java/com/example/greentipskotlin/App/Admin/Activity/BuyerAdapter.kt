package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.databinding.BuyerCardBinding

class BuyerAdapter(private var buyers:List<Buyer>):RecyclerView.Adapter<BuyerAdapter.buyerViewHolder>() {

    class buyerViewHolder(private val binding: BuyerCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(buyer: Buyer){
            binding.tvBuyerID.text=buyer.Buyer_Id.toString()
            binding.tvBuyerName.text=buyer.Buyer_Name
            binding.tvBuyerPhoneNumber.text=buyer.Buyer_PhoneNumber
            binding.tvBuyerCompanyName.text=buyer.Buyer_Company_Name
            binding.tvBuyerCompanyPhoneNumber.text=buyer.Buyer_Company_Number
            binding.tvBuyerCompanyAddress.text=buyer.Buyer_Company_Address
            binding.tvBuyerType.text=buyer.Buyer_Type
            binding.tvBuyerEmail.text=buyer.Buyer_Email
            binding.tvBuyerUsername.text=buyer.Buyer_Username
            binding.tvBuyerPassword.text=buyer.Buyer_password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): buyerViewHolder {
        val binding = BuyerCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return buyerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: buyerViewHolder, position: Int) {
        holder.bind(buyers[position])
    }

    override fun getItemCount(): Int =buyers.size

    fun updateList(newList: List<Buyer>) {
        buyers = newList
        notifyDataSetChanged()
    }


}