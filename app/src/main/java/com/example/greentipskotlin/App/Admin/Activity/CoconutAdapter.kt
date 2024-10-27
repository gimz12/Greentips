package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.databinding.CoconutCardBinding

class CoconutAdapter(private var coconuts: List<Coconut>):RecyclerView.Adapter<CoconutAdapter.CoconutViewHolder>() {

    class CoconutViewHolder(private val binding:CoconutCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(coconut: Coconut){
            binding.tvCoconutID.text = coconut.coconutId.toString()
            binding.tvCoconutEstateID.text = coconut.estateId.toString()
            binding.tvCoconutTreeType.text = coconut.coconutType
            binding.tvCoconutPlantedDate.text = coconut.plantDate
            binding.tvCoconutTreeAge.text = coconut.coconutTreeAge
            binding.tvCoconutTreeHealthStatus.text = coconut.treeHealthStatus
            binding.tvCoconutNumbers.text = coconut.numberOfCoconuts
            binding.tvCoconutAdditionalInfo.text = coconut.additionalCoconutInfo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoconutViewHolder {
        val binding = CoconutCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CoconutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoconutAdapter.CoconutViewHolder, position: Int) {
        holder.bind(coconuts[position])
    }

    override fun getItemCount():Int =coconuts.size

    fun updateList(newList: List<Coconut>) {
        coconuts = newList
        notifyDataSetChanged()
    }


}