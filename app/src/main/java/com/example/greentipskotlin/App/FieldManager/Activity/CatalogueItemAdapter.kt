package com.example.greentipskotlin.App.FieldManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.databinding.CatalogueItemCardBinding

class CatalogueItemAdapter(
    private var catalogues: List<Catalogue>,
    private val onEditClick: (Catalogue) -> Unit
) : RecyclerView.Adapter<CatalogueItemAdapter.catalogueViewHolder>() {

    class catalogueViewHolder(
        private val binding: CatalogueItemCardBinding,
        private val onEditClick: (Catalogue) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(catalogue: Catalogue) {
            binding.itemIdTextView.text = catalogue.Catalogue_Id.toString()
            binding.itemNameTextView.text = catalogue.Catalogue_Name
            binding.catalogueItemDescriptionTextView.text = catalogue.Catalogue_Item_Description
            binding.catalogueItemPriceTextView.text = catalogue.Catalogue_Item_Price.toString()
            binding.catalogueItemQuantityTextView.text = catalogue.Catalogue_Item_Quantity.toString()
            binding.catalogueItemTypeTextView.text = catalogue.Catalogue_Item_Type

            // Set click listener for "Edit Item" button
            binding.editBtn.setOnClickListener {
                onEditClick(catalogue)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): catalogueViewHolder {
        val binding = CatalogueItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return catalogueViewHolder(binding, onEditClick)
    }

    override fun onBindViewHolder(holder: catalogueViewHolder, position: Int) {
        holder.bind(catalogues[position])
    }

    override fun getItemCount(): Int = catalogues.size

    fun updateList(newList: List<Catalogue>) {
        catalogues = newList
        notifyDataSetChanged()
    }
}
