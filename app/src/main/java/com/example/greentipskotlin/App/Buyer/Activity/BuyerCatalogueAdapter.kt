package com.example.greentipskotlin.App.Buyer.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.BuyerCatalogueCardBinding

class BuyerCatalogueAdapter(
    private var catalogues: List<Catalogue>,
    private val onItemClick: (Catalogue) -> Unit
) : RecyclerView.Adapter<BuyerCatalogueAdapter.CatalogueViewHolder>() {

    class CatalogueViewHolder(
        private val binding: BuyerCatalogueCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catalogue: Catalogue, onItemClick: (Catalogue) -> Unit) {
            binding.itemNameTextView.text = catalogue.Catalogue_Name
            binding.catalogueItemDescriptionTextView.text = catalogue.Catalogue_Item_Description
            binding.catalogueItemPriceTextView.text = catalogue.Catalogue_Item_Price.toString()
            binding.catalogueItemQuantityTextView.text = catalogue.Catalogue_Item_Quantity.toString()

            // Load image with Glide
            Glide.with(binding.catalogueItemImageview.context)
                .load(catalogue.Catalogue_Item_Image) // Use the URI string
                .placeholder(R.drawable.buyer) // Optional: Add a placeholder image
                .error(R.drawable.warningbutton_background) // Optional: Add an error image
                .into(binding.catalogueItemImageview)

            // Set onClickListener for the entire card
            binding.root.setOnClickListener {
                onItemClick(catalogue)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val binding = BuyerCatalogueCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        holder.bind(catalogues[position], onItemClick)
    }

    override fun getItemCount(): Int = catalogues.size

    fun updateList(newList: List<Catalogue>) {
        catalogues = newList
        notifyDataSetChanged()
    }
}
