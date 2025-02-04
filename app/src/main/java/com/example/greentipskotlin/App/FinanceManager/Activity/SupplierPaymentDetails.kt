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
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivitySupplierOrderDetailsBinding
import com.example.greentipskotlin.databinding.ActivitySupplierPaymentDetailsBinding

class SupplierPaymentDetails : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierPaymentDetailsBinding

    private val model: SupplierViewModel by viewModels()
    private val modelSupplierPayment: SupplierPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierPaymentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paymentId = intent.getIntExtra("PAYMENT_ID", -1)
        val orderId = intent.getIntExtra("PAYMENT_ORDER_ID", -1)
        val userId = intent.getIntExtra("PAYMENT_USER_ID", -1)
        val date = intent.getStringExtra("PAYMENT_DATE")
        val time = intent.getStringExtra("PAYMENT_TIME")
        val paymentType  = intent.getStringExtra("PAYMENT_TYPE")
        val paymentStatus = intent.getStringExtra("PAYMENT_STATUS")
        val paidAmount = intent.getDoubleExtra("PAID_AMOUNT",0.0)
        val remainAmount = intent.getDoubleExtra("REMAIN_AMOUNT",0.0)
        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT",0.0)

        val supplier = model.getSupplierDetailsById(userId)

        binding.tvSupplierPaymentId.text="Supplier Payment ID: $paymentId"
        binding.tvSupplierOrderId.text="Supplier Order ID: $orderId"
        binding.tvSupplierId.text="Supplier ID: $userId"
        binding.tvSupplierName.text = supplier?.Supplier_Name.toString()
        binding.tvSupplierPhoneNumber.text = supplier?.Supplier_PhoneNumber.toString()
        binding.tvSupplierCompanyName.text = supplier?.Supplier_Company_Name
        binding.tvSupplierEmail.text = supplier?.Supplier_Email
        binding.tvPaymentDate.text="Payment Date: $date"
        binding.tvPaymentTime.text="Payment Time: $time"
        binding.tvPaymentTimeType.text="Payment Type: $paymentType"
        binding.tvPaymentStatus.text="Payment Status: $paymentStatus"
        binding.tvPaidAmount.text="Paid Amount: $paidAmount"
        binding.tvRemainAmount.text="Remain Amount: $remainAmount"
        binding.tvTotalAmount.text="Total Amount: $totalAmount"

        binding.makePayment.setOnClickListener {
            if (paymentStatus == "Paid") {
                Toast.makeText(this, "Payment is already completed. No further payments allowed.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payingAmount = binding.PayingAmount.text.toString().toDoubleOrNull()
            if (payingAmount == null || payingAmount <= 0) {
                binding.PayingAmount.error = "Enter a valid amount"
                return@setOnClickListener
            }

            if (payingAmount > remainAmount) {
                binding.PayingAmount.error = "Paying amount cannot exceed remaining amount"
                return@setOnClickListener
            }

            // Corrected Calculation of new amounts
            val newPaidAmount = paidAmount + payingAmount
            val newRemainAmount = totalAmount - newPaidAmount

            // Determine the new payment status
            val newPaymentStatus = if (newRemainAmount == 0.0) "Paid" else "Partial Paid"

            // Get the current date and time
            val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
            val currentTime = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())

            // Update the database
            val rowsUpdated = modelSupplierPayment.updateSupplierPayment(
                paymentId = paymentId,
                newPaymentDate = currentDate,
                newPaymentTime = currentTime,
                newPaidAmount = newPaidAmount,
                newRemainAmount = newRemainAmount,
                newPaymentStatus = newPaymentStatus
            )

            if (rowsUpdated > 0) {
                // Update UI
                binding.tvPaymentDate.text = "Payment Date: $currentDate"
                binding.tvPaymentTime.text = "Payment Time: $currentTime"
                binding.tvPaymentStatus.text = "Payment Status: $newPaymentStatus"
                binding.tvPaidAmount.text = "Paid Amount: $newPaidAmount"
                binding.tvRemainAmount.text = "Remain Amount: $newRemainAmount"
                binding.PayingAmount.text.clear()

                Toast.makeText(this, "Payment Updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed To Update Payment", Toast.LENGTH_SHORT).show()
            }
        }





    }
}