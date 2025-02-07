package com.example.greentipskotlin.App.CEO.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivitySupplierOrderHistoryBinding

class SupplierOrderHistory : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierOrderHistoryBinding

    private val model: SupplierViewModel by viewModels()
    private val modelSupplierPayment: SupplierPaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySupplierOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        val userId = intent.getIntExtra("USER_ID", -1)
        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemQuantity = intent.getIntExtra("ITEM_QUANTITY", -1)
        val estimateDelivery = intent.getStringExtra("ESTIMATE_DELIVERY_DATE")
        val quantityPrice = intent.getDoubleExtra("QUANTITY_PRICE", 0.0)

        val supplier = model.getSupplierDetailsById(userId)
        val payment = modelSupplierPayment.getSupplierPaymentByOrderId(orderId)

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

        binding.tvPaymentDate.text=payment?.PAYMENT_DATE
        binding.tvPaymentTime.text=payment?.PAYMENT_TIME
        binding.tvPaymentTimeType.text=payment?.PAYMENT_TYPE
        binding.tvPaymentStatus.text=payment?.PAYMENT_STATUS
        binding.tvPaidAmount.text=payment?.PAID_AMOUNT.toString()
        binding.tvRemainAmount.text=payment?.REMAIN_AMOUNT.toString()
        binding.tvTotalAmount.text=payment?.TOTAL_AMOUNT.toString()


    }
}