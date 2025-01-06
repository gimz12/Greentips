package com.example.greentipskotlin.App.FieldManager.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityCatalogueItemUpdateBinding

class CatalogueItemUpdate : AppCompatActivity() {

    private lateinit var binding:ActivityCatalogueItemUpdateBinding
    private val model:CatalogueViewModel by viewModels()
    private var catalogueId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCatalogueItemUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val catalogueId = intent.getIntExtra("CATALOGUE_ID", -1)
        val catalogueType = intent.getStringExtra("CATALOGUE_TYPE")

        if (catalogueId == -1) {
            Toast.makeText(this, "Invalid Catalogue ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val existingItem = model.getItemByCatalogueId(catalogueId)
        existingItem?.let {
            populateItemDetails(it)
        }

        binding.updateItemBtn.setOnClickListener {
            saveUpdatedItemDetails(existingItem)
        }

    }

    fun populateItemDetails(catalogue: Catalogue) {
        binding.itemNameTxt.setText(catalogue.Catalogue_Name)

        val itemTypeIndex = resources.getStringArray(R.array.catalog_item_type_string)
            .indexOf(catalogue.Catalogue_Item_Type)
        if (itemTypeIndex >= 0) {
            binding.catalogueItemTypeSpinner.setSelection(itemTypeIndex)
        }
        binding.catalogueItemPriceTxt.setText(catalogue.Catalogue_Item_Price.toString())
        binding.catalogueItemQuantityTxt.setText(catalogue.Catalogue_Item_Quantity.toString())
        binding.catalogueItemDescriptionTxt.setText(catalogue.Catalogue_Item_Description)

        val availabilityIndex = resources.getStringArray(R.array.catalog_item_availability_string)
            .indexOf(catalogue.Catalogue_Item_Availability)
        if (availabilityIndex >= 0) {
            binding.catalogueItemAvailabilitySpinner.setSelection(availabilityIndex)
        }
    }


    private fun saveUpdatedItemDetails(existingItem: Catalogue?) {
        if (existingItem == null) {
            Toast.makeText(this, "Item data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedItem = existingItem.copy(
            Catalogue_Name = binding.itemNameTxt.text.toString().ifBlank { existingItem.Catalogue_Name },
            Catalogue_Item_Type = binding.catalogueItemTypeSpinner.selectedItem.toString().ifBlank { existingItem.Catalogue_Item_Type },
            Catalogue_Item_Price = binding.catalogueItemPriceTxt.text.toString().toDoubleOrNull() ?: existingItem.Catalogue_Item_Price,
            Catalogue_Item_Quantity = binding.catalogueItemQuantityTxt.text.toString().toIntOrNull() ?: existingItem.Catalogue_Item_Quantity,
            Catalogue_Item_Description = binding.catalogueItemDescriptionTxt.text.toString().ifBlank { existingItem.Catalogue_Item_Description },
            Catalogue_Item_Availability = binding.catalogueItemAvailabilitySpinner.selectedItem.toString().ifBlank { existingItem.Catalogue_Item_Availability },
        )

        // Update Catalogue Item using ViewModel
        val rowsAffected = model.updateCatalogueItem(updatedItem)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Item updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update Item", Toast.LENGTH_SHORT).show()
        }
    }
}