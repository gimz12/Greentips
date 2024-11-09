package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.databinding.IntercropCardBinding

class IntercropAdapter(private var intercrops:List<Intercrops>):RecyclerView.Adapter<IntercropAdapter.IntercropViewHolder>() {

    class IntercropViewHolder(private val binding: IntercropCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind (intercrops: Intercrops){
            binding.tvIntercropID.text= intercrops.intercropId.toString()
            binding.tvIntercropType.text = intercrops.intercropType
            binding.tvIntercropPlantedDate.text = intercrops.intercropPlantedDate
            binding.tvIntercropEstateID.text = intercrops.intercropEstateID.toString()
            binding.tvCoconutAdditionalInfo.text = intercrops.intercropAddtionalInfo
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntercropViewHolder {
        val binding = IntercropCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IntercropViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntercropViewHolder, position: Int) {
        holder.bind(intercrops[position])
    }

    override fun getItemCount(): Int = intercrops.size

    fun updateList(newList: List<Intercrops>) {
        intercrops = newList
        notifyDataSetChanged()
    }




}