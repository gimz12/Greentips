package com.example.greentipskotlin.App.CEO.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.BuyerPaymentViewModel
import com.example.greentipskotlin.App.Admin.viewModel.BuyerViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityBuyerOrderHistoryBinding
import com.example.greentipskotlin.databinding.ActivitySupplierOrderHistoryBinding

class BuyerOrderHistory : AppCompatActivity() {

    private lateinit var binding:ActivityBuyerOrderHistoryBinding
    private val modelBuyerPaymentViewModel: BuyerPaymentViewModel by viewModels()
    private val modelBuyerViewModel: BuyerViewModel by viewModels()
    private val modelBuyerOrderViewModel: BuyerOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBuyerOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        val userId = intent.getIntExtra("USER_ID", -1)

        val buyerOrder = modelBuyerOrderViewModel.getBuyerOrderById(orderId)

        // Fetch buyer payment details
        val buyerPayment = modelBuyerPaymentViewModel.getBuyerPaymentByOrderId(orderId)

        // Fetch user details
        val userDetails = modelBuyerViewModel.getBuyerDetailsById(userId)

        binding.tvBuyerOrderId.text=buyerOrder?.ORDER_ID.toString()
        binding.tvBuyerId.text=userId.toString()
        binding.tvBuyerEmail.text=userDetails?.Buyer_Email
        binding.tvOrderStatus.text=buyerOrder?.ORDER_STATUS
        binding.tvBuyerShippingAddress.text=buyerOrder?.ORDER_SHIPPING_ADDRESS
        binding.tvPaymentDateTime.text=buyerPayment?.PAYMENT_DATE_TIME
        binding.tvPaymentType.text=buyerPayment?.PAYMENT_METHOD
        binding.tvPaymentStatus.text=buyerPayment?.PAYMENT_STATUS
        binding.tvPaidAmount.text=buyerPayment?.PAYMENT_AMOUNT.toString()



    }
}