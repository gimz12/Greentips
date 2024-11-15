package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.databinding.ResourceCardBinding

class ResourceAdapter(
    private var resources: List<Resources>,
    private val onItemClick: (Resources) -> Unit
) : RecyclerView.Adapter<ResourceAdapter.ResourceViewHolder>() {

    class ResourceViewHolder(
        private val binding: ResourceCardBinding,
        private val onItemClick: (Resources) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resource: Resources) {
            binding.tvResourceID.text = resource.resourcesID.toString()
            binding.tvResourceDescription.text = resource.description
            binding.tvResourceDate.text = resource.date
            binding.tvResourceBillNumber.text = resource.billNumber
            binding.tvResourceAmount.text = resource.amount.toString()
            binding.tvResourceEstateID.text = resource.resourcesEstate.toString()

            binding.root.setOnClickListener {
                onItemClick(resource) // Trigger the click listener with resource data
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val binding = ResourceCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResourceViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resources[position])
    }

    override fun getItemCount(): Int = resources.size

    fun updateList(newList: List<Resources>) {
        resources = newList
        notifyDataSetChanged()
    }
}
