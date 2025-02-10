package com.example.greentipskotlin.App.FinanceManager.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivitySupplierPaymentDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SupplierPaymentDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierPaymentDetailsBinding

    // Track payment state with member variables
    private var paidAmount: Double = 0.0
    private var remainAmount: Double = 0.0
    private val totalAmount: Double by lazy { intent.getDoubleExtra("TOTAL_AMOUNT", 0.0) }
    private var currentPaymentStatus: String = ""

    private val model: SupplierViewModel by viewModels()
    private val modelSupplierPayment: SupplierPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierPaymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize payment state from intent
        paidAmount = intent.getDoubleExtra("PAID_AMOUNT", 0.0)
        remainAmount = intent.getDoubleExtra("REMAIN_AMOUNT", 0.0)
        currentPaymentStatus = intent.getStringExtra("PAYMENT_STATUS") ?: ""

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        val paymentId = intent.getIntExtra("PAYMENT_ID", -1)
        val orderId = intent.getIntExtra("PAYMENT_ORDER_ID", -1)
        val userId = intent.getIntExtra("PAYMENT_USER_ID", -1)
        val date = intent.getStringExtra("PAYMENT_DATE")
        val time = intent.getStringExtra("PAYMENT_TIME")
        val paymentType = intent.getStringExtra("PAYMENT_TYPE")

        val supplier = model.getSupplierDetailsById(userId)

        // Set initial UI values
        binding.apply {
            tvSupplierPaymentId.text = "Supplier Payment ID: $paymentId"
            tvSupplierOrderId.text = "Supplier Order ID: $orderId"
            tvSupplierId.text = "Supplier ID: $userId"
            tvSupplierName.text = supplier?.Supplier_Name.toString()
            tvSupplierPhoneNumber.text = supplier?.Supplier_PhoneNumber.toString()
            tvSupplierCompanyName.text = supplier?.Supplier_Company_Name
            tvSupplierEmail.text = supplier?.Supplier_Email
            tvPaymentDate.text = "Payment Date: $date"
            tvPaymentTime.text = "Payment Time: $time"
            tvPaymentTimeType.text = "Payment Type: $paymentType"
            tvPaymentStatus.text = "Payment Status: $currentPaymentStatus"
            tvPaidAmount.text = "Paid Amount: ${"%.2f".format(paidAmount)}"
            tvRemainAmount.text = "Remain Amount: ${"%.2f".format(remainAmount)}"
            tvTotalAmount.text = "Total Amount: ${"%.2f".format(totalAmount)}"
        }
    }

    private fun setupClickListeners() {
        binding.makePayment.setOnClickListener {
            if (currentPaymentStatus == "Paid") {
                Toast.makeText(this, "Payment already completed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payingAmount = binding.PayingAmount.text.toString().toDoubleOrNull() ?: 0.0.also {
                binding.PayingAmount.error = "Invalid amount"
                return@setOnClickListener
            }

            if (payingAmount <= 0) {
                binding.PayingAmount.error = "Amount must be positive"
                return@setOnClickListener
            }

            if (payingAmount > remainAmount) {
                binding.PayingAmount.error = "Cannot pay more than remaining amount"
                return@setOnClickListener
            }

            // Update payment state
            paidAmount = (paidAmount + payingAmount).roundToTwoDecimals()
            remainAmount = (totalAmount - paidAmount).roundToTwoDecimals()
            currentPaymentStatus = if (remainAmount == 0.0) "Paid" else "Partial Paid"

            updatePaymentInDatabase()
        }
    }

    private fun Double.roundToTwoDecimals() = "%.2f".format(this).toDouble()

    private fun updatePaymentInDatabase() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        CoroutineScope(Dispatchers.IO).launch {
            val rowsUpdated = modelSupplierPayment.updateSupplierPayment(
                paymentId = intent.getIntExtra("PAYMENT_ID", -1),
                newPaymentDate = currentDate,
                newPaymentTime = currentTime,
                newPaidAmount = paidAmount,
                newRemainAmount = remainAmount,
                newPaymentStatus = currentPaymentStatus
            )

            runOnUiThread {
                if (rowsUpdated > 0) {
                    updateUI(currentDate, currentTime)
                    sendEmailNotification(currentDate, currentTime)
                    binding.PayingAmount.text?.clear()
                    Toast.makeText(this@SupplierPaymentDetails, "Payment updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SupplierPaymentDetails, "Update failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(date: String, time: String) {
        binding.apply {
            tvPaymentDate.text = "Payment Date: $date"
            tvPaymentTime.text = "Payment Time: $time"
            tvPaymentStatus.text = "Payment Status: $currentPaymentStatus"
            tvPaidAmount.text = "Paid Amount: ${"%.2f".format(paidAmount)}"
            tvRemainAmount.text = "Remain Amount: ${"%.2f".format(remainAmount)}"
        }
    }

    private fun sendEmailNotification(date: String, time: String) {
        val supplier = model.getSupplierDetailsById(intent.getIntExtra("PAYMENT_USER_ID", -1))
        CoroutineScope(Dispatchers.IO).launch {
            EmailHelper.notifySupplierPartialPayment(
                supplierEmail = supplier?.Supplier_Email ?: "kumalillankoon12@gmail.com",
                paymentId = intent.getIntExtra("PAYMENT_ID", -1),
                orderId = intent.getIntExtra("PAYMENT_ORDER_ID", -1),
                supplierName = supplier?.Supplier_Name ?: "Supplier",
                paymentDate = date,
                paymentTime = time,
                paymentType = intent.getStringExtra("PAYMENT_TYPE") ?: "Bank Transfer",
                paymentStatus = currentPaymentStatus,
                paidAmount = paidAmount,
                remainingAmount = remainAmount,
                totalAmount = totalAmount
            )
        }
    }
}