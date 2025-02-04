package com.example.greentipskotlin.App.FinanceManager.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.App.Model.SupplierPayment
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityOrderDetailsBinding
import com.example.greentipskotlin.databinding.ActivitySupplierOrderDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SupplierOrderDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierOrderDetailsBinding

    private val model:SupplierViewModel by viewModels()
    private val modelSupplierPayment:SupplierPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        val userId = intent.getIntExtra("USER_ID", -1)
        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemQuantity = intent.getIntExtra("ITEM_QUANTITY", -1)
        val estimateDelivery = intent.getStringExtra("ESTIMATE_DELIVERY_DATE")
        val quantityPrice = intent.getDoubleExtra("QUANTITY_PRICE", 0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val finalPrice = totalAmount + (totalAmount * 10 / 100)

        val supplier = model.getSupplierDetailsById(userId)

        binding.supplierTitle.text = "$itemName Supply"
        binding.supplierSubtitle.text = supplier?.Supplier_Company_Name

        binding.tvSupplierOrderId.text = "Order ID: $orderId"
        binding.tvSupplierId.text = "Supplier ID: $userId"
        binding.tvSupplierName.text = supplier?.Supplier_Name.toString()
        binding.tvSupplierPhoneNumber.text = supplier?.Supplier_PhoneNumber.toString()
        binding.tvSupplierCompanyName.text = supplier?.Supplier_Company_Name
        binding.tvSupplierEmail.text = supplier?.Supplier_Email
        binding.tvSupplierOrderItem.text = "Item Name: $itemName"
        binding.tvSupplierOrderItemQuantity.text = "Item Quantity: $itemQuantity"
        binding.tvSupplierOrderItemPrice.text = "Item Price: $quantityPrice"
        binding.tvSupplierEstimateDeliveryDate.text = "Estimate Delivery Date: $estimateDelivery"
        binding.tvSupplierOrderTotalPrice.text = "Total Amount: $totalAmount"
        binding.tvFinalPrice.text = "Final Amount: $finalPrice"

        // Set OnClickListener for Confirm Button
        binding.makePayment.setOnClickListener {
            val payingAmountText = binding.PayingAmount.text.toString()

            // Validate Paying Amount
            if (payingAmountText.isEmpty()) {
                binding.PayingAmount.error = "Enter a valid amount"
                return@setOnClickListener
            }

            val payingAmount = payingAmountText.toDouble()
            if (payingAmount > finalPrice) {
                binding.PayingAmount.error = "Amount cannot exceed $finalPrice"
                return@setOnClickListener
            }

            // Get Current Date and Time
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

            // Calculate Remaining Amount and Status
            val remainingAmount = finalPrice - payingAmount
            val paymentStatus = if (remainingAmount > 0) "Partial Paid" else "Paid"

            // Create SupplierPayment Object
            val supplierPayment = SupplierPayment(
                PAYMENT_ID = null, // Auto-generated or provide the ID
                PAYMENT_ORDER_ID = orderId,
                PAYMENT_USER_ID = userId,
                PAYMENT_DATE = currentDate,
                PAYMENT_TIME = currentTime,
                PAYMENT_TYPE = "Bank Transfer", // Set payment type
                PAYMENT_STATUS = paymentStatus,
                REMAIN_AMOUNT = remainingAmount,
                PAID_AMOUNT = payingAmount,
                TOTAL_AMOUNT = finalPrice
            )

            // Insert into Database
            modelSupplierPayment.insertSupplierOrder(supplierPayment)

            Toast.makeText(this, "Payment recorded successfully!", Toast.LENGTH_SHORT).show()
            finish() // Close the activity
        }
    }

}