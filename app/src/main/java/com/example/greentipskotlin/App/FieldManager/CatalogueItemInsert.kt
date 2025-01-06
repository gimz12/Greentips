package com.example.greentipskotlin.App.FieldManager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.databinding.ActivityCatalogueItemInsertBinding

class CatalogueItemInsert : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogueItemInsertBinding
    private val model: CatalogueViewModel by viewModels()
    private var profileImageUri: Uri? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogueItemInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Availability options
        val buyerAvailabilityOptions = arrayOf("Available", "Unavailable")
        val supplierAvailabilityOptions = arrayOf("Need", "No Need")

        // Handle catalogue type selection
        binding.catalogueItemTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                updatePriceField(selectedType)
                updateAvailabilitySpinner(selectedType, buyerAvailabilityOptions, supplierAvailabilityOptions)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Image selection button
        binding.selectImageBtn.setOnClickListener { openImagePicker() }

        // Add item button
        binding.addItemBtn.setOnClickListener { addCatalogueItem() }
    }

    private fun updatePriceField(selectedType: String) {
        if (selectedType == "Buyer") {
            binding.catalogueItemPriceTxt.isEnabled = true
            binding.catalogueItemPriceTxt.hint = "Enter Price Per Kg"
        } else if (selectedType == "Supplier") {
            binding.catalogueItemPriceTxt.isEnabled = false
            binding.catalogueItemPriceTxt.text.clear()
            binding.catalogueItemPriceTxt.hint = "Price not applicable"
        }
    }

    private fun updateAvailabilitySpinner(
        selectedType: String,
        buyerOptions: Array<String>,
        supplierOptions: Array<String>
    ) {
        val availabilityAdapter = if (selectedType == "Buyer") {
            ArrayAdapter(this, android.R.layout.simple_spinner_item, buyerOptions)
        } else {
            ArrayAdapter(this, android.R.layout.simple_spinner_item, supplierOptions)
        }
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.catalogueItemAvailabilitySpinner.adapter = availabilityAdapter
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            profileImageUri = data?.data
            profileImageUri?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                binding.itemImageView.setImageURI(it)
            } ?: Toast.makeText(this, "Image selection failed. Try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addCatalogueItem() {
        val catalogueName = binding.itemNameTxt.text.toString()
        val catalogueType = binding.catalogueItemTypeSpinner.selectedItem.toString()
        val cataloguePrice = binding.catalogueItemPriceTxt.text.toString()
        val catalogueQuantity = binding.catalogueItemQuantityTxt.text.toString()
        val catalogueDescription = binding.catalogueItemDescriptionTxt.text.toString()
        val catalogueAvailability = binding.catalogueItemAvailabilitySpinner.selectedItem?.toString()
        val catalogueItemImage = profileImageUri?.toString()

        // Validation
        if (catalogueName.isEmpty() || catalogueQuantity.isEmpty() || catalogueDescription.isEmpty() || catalogueAvailability.isNullOrEmpty()) {
            Toast.makeText(this, "All fields except Price are required.", Toast.LENGTH_SHORT).show()
            return
        }

        if (catalogueType == "Buyer" && cataloguePrice.isEmpty()) {
            Toast.makeText(this, "Price is required for Buyer.", Toast.LENGTH_SHORT).show()
            return
        }

        if (catalogueType == "Supplier" && cataloguePrice.isNotEmpty()) {
            Toast.makeText(this, "Price should not be entered for Supplier.", Toast.LENGTH_SHORT).show()
            return
        }

        val cataloguePriceDouble = if (cataloguePrice.isNotEmpty()) cataloguePrice.toDouble() else 0.0
        val catalogueQuantityInt = catalogueQuantity.toIntOrNull() ?: run {
            Toast.makeText(this, "Quantity must be a valid number.", Toast.LENGTH_SHORT).show()
            return
        }

        model.insertCatalogue(
            Catalogue(
                null,
                catalogueName,
                catalogueType,
                cataloguePriceDouble,
                catalogueQuantityInt,
                catalogueDescription,
                catalogueAvailability,
                catalogueItemImage
            )
        )

        Toast.makeText(this, "Catalogue Item Inserted", Toast.LENGTH_SHORT).show()
        finish() // Close the activity
    }
}
