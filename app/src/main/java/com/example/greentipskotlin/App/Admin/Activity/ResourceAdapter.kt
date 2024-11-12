package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.databinding.ResourceCardBinding

class ResourceAdapter(private var resources:List<Resources>):RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {

    class ResourceViewHolder(private val binding: ResourceCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(resources: Resources){
            binding.tvResourceID.text = resources.resourcesID.toString()
            binding.tvResourceDescription.text = resources.description
            binding.tvResourceDate.text = resources.date
            binding.tvResourceBillNumber.text = resources.billNumber
            binding.tvResourceAmount.text = resources.amount.toString()
            binding.tvResourceEstateID.text = resources.resourcesEstate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val binding = ResourceCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ResourceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
       holder.bind(resources[position])
    }

    override fun getItemCount(): Int = resources.size

    fun updateList(newList:List<Resources>){
        resources = newList
        notifyDataSetChanged()
    }

}