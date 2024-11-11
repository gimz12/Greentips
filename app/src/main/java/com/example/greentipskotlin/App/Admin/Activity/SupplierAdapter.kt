package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.Supplier
import com.example.greentipskotlin.databinding.SupplierCardBinding

class SupplierAdapter(private var suppliers:List<Supplier>):RecyclerView.Adapter<SupplierAdapter.supplierViewHolder>() {

    class supplierViewHolder(private val binding: SupplierCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(supplier: Supplier){
            binding.tvSupplierID.text = supplier.Supplier_Id.toString()
            binding.tvSupplierName.text = supplier.Supplier_Name
            binding.tvSupplierPhoneNumber.text = supplier.Supplier_PhoneNumber
            binding.tvSupplierCompanyName.text = supplier.Supplier_Company_Name
            binding.tvSupplierCompanyPhoneNumber.text = supplier.Supplier_Company_Number
            binding.tvSupplierCompanyAddress.text = supplier.Supplier_Company_Address
            binding.tvSupplierType.text = supplier.Supplier_Type
            binding.tvSupplierEmail.text = supplier.Supplier_Email
            binding.tvSupplierUsername.text = supplier.Supplier_Username
            binding.tvSupplierPassword.text = supplier.Supplier_password
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): supplierViewHolder {
        val binding = SupplierCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return supplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: supplierViewHolder, position: Int) {
        holder.bind(suppliers[position])
    }

    override fun getItemCount(): Int = suppliers.size

    fun updateList(newList: List<Supplier>) {
        suppliers = newList
        notifyDataSetChanged()
    }



}