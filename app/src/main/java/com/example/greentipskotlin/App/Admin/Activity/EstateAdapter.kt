package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Admin.Activity.EmployeeAdapter.EmployeeViewHolder
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.databinding.EmployeeCardBinding
import com.example.greentipskotlin.databinding.EstateCardBinding

class EstateAdapter(private var estates:List<Estate>):
    RecyclerView.Adapter<EstateAdapter.EstateViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder{
        val binding = EstateCardBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return EstateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstateAdapter.EstateViewHolder, position: Int) {
        holder.bind(estates[position])
    }

    override fun getItemCount(): Int =estates.size


    fun updateList(newList: List<Estate>) {
        estates = newList
        notifyDataSetChanged()
    }

    class EstateViewHolder(private val binding: EstateCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(estate: Estate) {
            binding.tvEstateID.text = estate.estateId.toString()
            binding.tvEstateName.text = estate.estateName
            binding.tvEstateLocation.text = estate.estateLocation
            binding.tvEstateSize.text = estate.estateSize
            binding.tvEstateAdditionalInfo.text =estate.estateAdditionalInfo


        }
    }

}