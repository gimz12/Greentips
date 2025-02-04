package com.example.greentipskotlin.App.Supplier.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.SupplierOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityUpdateSupplierOfferBinding

class UpdateSupplierOffer : AppCompatActivity() {

    private lateinit var binding:ActivityUpdateSupplierOfferBinding

    private val model:SupplierOrderViewModel by viewModels()
    private val modelPayment:SupplierPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateSupplierOfferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from Intent
        val orderId = intent.getIntExtra("ORDER_ID", 0)
        val userId = intent.getIntExtra("USER_ID", 0)
        val itemName = intent.getStringExtra("ITEM_NAME") ?: ""
        val itemDescription = intent.getStringExtra("ITEM_DESCRIPTION") ?: ""
        val itemQuantity = intent.getIntExtra("ITEM_QUANTITY", 0)
        val estimateDeliveryDate = intent.getStringExtra("ESTIMATE_DELIVERY_DATE") ?: ""
        val quantityPrice = intent.getDoubleExtra("QUANTITY_PRICE", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val fieldManagerStatus = intent.getStringExtra("FIELD_MANAGER_STATUS") ?: ""
        val ceoStatus = intent.getStringExtra("CEO_STATUS") ?: ""



        // Set data to views
        binding.usernameTextView.text = "User ID: $userId"
        binding.orderIdTextView.text = "Order ID: $orderId"
        binding.itemNameTextView.text = "Item Name: $itemName"
        binding.teItemQuantity.setText(itemQuantity.toString())
        binding.teEstimateDelivery.setText(estimateDeliveryDate)
        binding.teQuantityPrice.setText(quantityPrice.toString())

        // Disable update/delete if FieldManager status is approved
        if (fieldManagerStatus == "Approved") {
            binding.buttonUpdate.isEnabled = false
            binding.buttonDelete.isEnabled = false
        }

        modelPayment.getPaymentStatus(orderId)?.let {
            // If payment status exists, update the TextView
            binding.tvPayment.text = "Payment Status: $it"
        } ?: run {
            // If payment status is null, set to "Pending"
            binding.tvPayment.text = "Payment Status: Pending"
        }

        // Update button click listener
        binding.buttonUpdate.setOnClickListener {
            val updatedQuantity = binding.teItemQuantity.text.toString().toInt()
            val updatedEstimateDelivery = binding.teEstimateDelivery.text.toString()
            val updatedQuantityPrice = binding.teQuantityPrice.text.toString().toDouble()

            // Create the updated SupplierOrder object
            val updatedSupplierOrder = SupplierOrder(
                ORDER_ID = orderId,
                USER_ID = userId,
                ITEM_NAME = itemName,
                ITEM_DESCRIPTION = itemDescription,
                ITEM_QUANTITY = updatedQuantity,
                ESTIMATE_DELIVERY_DATE = updatedEstimateDelivery,
                QUANTITY_PRICE = updatedQuantityPrice,
                TOTAL_AMOUNT = totalAmount,
                FIELDMANAGER_STATUS = fieldManagerStatus,
                CEO_STATUS = ceoStatus
            )

            // Call the ViewModel to update the order
            model.updateSupplierOrder(updatedSupplierOrder)
        }

        // Delete button click listener
        binding.buttonDelete.setOnClickListener {
            model.deleteSupplierOrder(orderId)
        }
    }
}