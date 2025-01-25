package com.example.greentipskotlin.App.Supplier.Activity


import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivitySupplyDetailsBinding
import java.text.SimpleDateFormat
import java.util.*

class SupplyDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySupplyDetailsBinding
    private val catalogueViewModel: CatalogueViewModel by viewModels()
    private val model: SupplierOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve intent extras
        val name = intent.getStringExtra("CATALOGUE_NAME")
        val description = intent.getStringExtra("CATALOGUE_DESCRIPTION")
        val imageUri = intent.getStringExtra("CATALOGUE_IMAGE")

        // Set the initial data
        binding.detailedName.text = name ?: "N/A"
        binding.detailedDes.text = description ?: "N/A"

        if (imageUri != null) {
            binding.detailedImg.setImageURI(Uri.parse(imageUri))
        } else {
            binding.detailedImg.setImageResource(R.drawable.warningbutton_background)
        }

        // Listen to changes in price and quantity fields
        binding.priceTxt.addTextChangedListener(createTextWatcher())
        binding.quantityTxt.addTextChangedListener(createTextWatcher())

        // Listen to changes in estimated delivery date
        binding.estimatedDeliveryTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validateDate()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Make Offer button click listener
        binding.makeOffer.setOnClickListener {
            val pricePerKg = binding.priceTxt.text.toString().toDoubleOrNull()
            val quantity = binding.quantityTxt.text.toString().toIntOrNull()

            // Validations for price and quantity
            if (pricePerKg == null || pricePerKg <= 0) {
                Toast.makeText(this, "Please enter a valid price per kg.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (quantity == null || quantity <= 0) {
                Toast.makeText(this, "Please enter a valid quantity.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalPrice = pricePerKg * quantity
            val estimatedDeliveryDate = binding.estimatedDeliveryTxt.text.toString()

            // Validating that the selected date is not in the past
            if (!isDateValid(estimatedDeliveryDate)) {
                Toast.makeText(this, "The estimated delivery date cannot be before today.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("USER_ID", -1)

            if (userId == -1) {
                Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create the SupplierOrder object
            val supplierOrder = SupplierOrder(
                ORDER_ID = null,
                USER_ID = userId,
                ITEM_NAME = name ?: "N/A",
                ITEM_QUANTITY = quantity,
                ITEM_DESCRIPTION = description ?: "N/A",
                ESTIMATE_DELIVERY_DATE = estimatedDeliveryDate,
                QUANTITY_PRICE = pricePerKg,
                TOTAL_AMOUNT = totalPrice,
                FIELDMANAGER_STATUS = "Pending",  // Status should be "Pending" initially
                CEO_STATUS = "Pending"  // Status should be "Pending" initially
            )

            // Insert the SupplierOrder
            model.insertSupplierOrder(supplierOrder)

            // Show success message
            Toast.makeText(this, "Offer placed successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun createTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateTotalPrice()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private fun updateTotalPrice() {
        val pricePerKg = binding.priceTxt.text.toString().toDoubleOrNull() ?: 0.0
        val quantity = binding.quantityTxt.text.toString().toDoubleOrNull() ?: 0.0
        val totalPrice = pricePerKg * quantity
        binding.totalPrice.text = "Total Price: $totalPrice"
    }

    private fun isDateValid(date: String): Boolean {
        try {
            val inputFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val inputDate = inputFormat.parse(date)

            val calendar = Calendar.getInstance()
            val today = calendar.time

            // Ensure the input date is not before today
            return inputDate != null && !inputDate.before(today)
        } catch (e: Exception) {
            return false  // If date parsing fails, it's invalid
        }
    }

    private fun validateDate() {
        val dateText = binding.estimatedDeliveryTxt.text.toString()

        if (!isDateValid(dateText)) {
            binding.estimatedDeliveryTxt.error = "The date cannot be in the past"
        } else {
            binding.estimatedDeliveryTxt.error = null
        }
    }
}
