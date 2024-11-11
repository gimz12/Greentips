package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.databinding.HarvestinfoCardBinding

class HarvestAdapter(private var harvestInfos:List<HarvestInfo>):RecyclerView.Adapter<HarvestAdapter.HarvestViewHolder>() {

    class HarvestViewHolder(private val binding: HarvestinfoCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(harvestInfo: HarvestInfo){
            binding.tvHarvestInfoID.text=harvestInfo.harvestID.toString()
            binding.tvHarvestEstateID.text = harvestInfo.harvestEstate.toString()
            binding.tvHarvestDate.text = harvestInfo.harvestDate
            binding.tvHarvestQuantity.text = harvestInfo.harvestQuantity.toString()
            binding.tvHarvestCropType.text = harvestInfo.harvestType
            binding.tvHarvestInfoAdditionalInfo.text = harvestInfo.harvestAddtional_Info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarvestViewHolder {
        val binding = HarvestinfoCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HarvestViewHolder(binding)

    }

    override fun onBindViewHolder(holder: HarvestViewHolder, position: Int) {
        holder.bind(harvestInfos[position])
    }

    override fun getItemCount(): Int =harvestInfos.size

    fun updateList(newList: List<HarvestInfo>){
        harvestInfos = newList
        notifyDataSetChanged()
    }



}