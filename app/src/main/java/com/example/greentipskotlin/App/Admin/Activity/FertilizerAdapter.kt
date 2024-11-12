package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Fertilizer
import com.example.greentipskotlin.databinding.FertilizerCardBinding

class FertilizerAdapter(private var fertilizers:List<Fertilizer>):RecyclerView.Adapter<FertilizerAdapter.FertilizerViewHolder>() {

    class FertilizerViewHolder(private val binding: FertilizerCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(fertilizer: Fertilizer){
            binding.tvFertilizerID.text = fertilizer.fertilizerId.toString()
            binding.tvFertilizerName.text= fertilizer.fertilizerName
            binding.tvFertilizerType.text = fertilizer.fertilizerType
            binding.tvFertilizerReceivedDate.text = fertilizer.fertilizerDate
            binding.tvFertilizerQuantity.text = fertilizer.fertilizerQuantity
            binding.tvFertilizerComposition.text = fertilizer . fertilizerComposition
            binding.tvFertilizerAdditionalInfo.text = fertilizer.fertilizerAdditionalInfo
            binding.tvFertilizerEstateID.text = fertilizer.fertilizerEstateId.toString()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FertilizerViewHolder {
        val binding = FertilizerCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FertilizerViewHolder(binding)
    }


    override fun onBindViewHolder(holder: FertilizerViewHolder, position: Int) {
        holder.bind(fertilizers[position])
    }

    override fun getItemCount(): Int = fertilizers.size

    fun updateList(newList: List<Fertilizer>){
        fertilizers = newList
        notifyDataSetChanged()
    }
}