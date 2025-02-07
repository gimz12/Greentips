package com.example.greentipskotlin.App.FieldManager.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.OrderItemViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityOrderDetailsBinding
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetails : AppCompatActivity() {

    private lateinit var binding:ActivityOrderDetailsBinding
    private val model:OrderItemViewModel by viewModels()
    private val buyerOrderViewModel:BuyerOrderViewModel by viewModels()

    private lateinit var orderItemAdapter: BuyerOrderItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID",-1)
        val orderCost = intent.getDoubleExtra("ORDER_COST",0.0)
        val orderDate = intent.getStringExtra("ORDER_DATE")
        val orderShippingAddress = intent.getStringExtra("ORDER_SHIPPING_ADDRESS")

        val orderStatus = buyerOrderViewModel.getOrderStatus(orderId) ?: "Processing"
        updateButtonStates(orderStatus)

        // Set data to TextViews
        binding.orderIdTv.text = "Order ID: $orderId"
        binding.orderCostTv.text = "Order Cost: $orderCost"
        binding.orderDateTv.text = "Order Date: $orderDate"
        binding.orderStatusTv.text = "Order Status: $orderStatus"
        binding.orderShippingAddressTv.text = "Shipping Address: $orderShippingAddress"

        orderItemAdapter = BuyerOrderItemAdapter(emptyList())
        binding.recyclerview.layoutManager=LinearLayoutManager(this)
        binding.recyclerview.adapter=orderItemAdapter

        model.orderItems.observe(this, { items ->
            orderItemAdapter.updateList(items)  // Update RecyclerView when data changes
        })

        buyerOrderViewModel.orderStatus.observe(this, { currentStatus ->
            binding.orderStatusTv.text = "Order Status: $currentStatus"
            updateButtonStates(currentStatus)
        })

        // Load the current order status
        buyerOrderViewModel.loadOrderStatus(orderId)


        binding.dispatchBtn.setOnClickListener {
            if (orderStatus in listOf("Processing")) {
                if (buyerOrderViewModel.updateOrderStatus(orderId, "Dispatched")) {
                    updateButtonStates("Dispatched")

                    CoroutineScope(Dispatchers.IO).launch {
                        val invoiceSent = EmailHelper.sendOrderDispatchedEmail(
                            "kumalillankoon12@gmail.com",
                            orderId
                        )
                        withContext(Dispatchers.Main) {  // Update UI on main thread
                            if (invoiceSent) {
                                println("Invoice email sent successfully.")
                            } else {
                                println("Failed to send invoice email.")
                            }
                        }
                    }

                }
            }
        }

        binding.deliveredBtn.setOnClickListener {
            if (orderStatus == "Dispatched") {
                if (buyerOrderViewModel.updateOrderStatus(orderId, "Delivered")) {
                    updateButtonStates("Delivered")

                    CoroutineScope(Dispatchers.IO).launch {
                        val invoiceSent = EmailHelper.sendOrderDeliveredEmail(
                            "kumalillankoon12@gmail.com",
                            orderId
                        )
                        withContext(Dispatchers.Main) {  // Update UI on main thread
                            if (invoiceSent) {
                                println("Invoice email sent successfully.")
                            } else {
                                println("Failed to send invoice email.")
                            }
                        }
                    }
                }
            }
        }

        binding.cancelBtn.setOnClickListener {
            if (orderStatus !in listOf("Delivered", "Cancelled")) {
                showCancelConfirmationDialog(orderId)
            }

            CoroutineScope(Dispatchers.IO).launch {
                val invoiceSent = EmailHelper.sendOrderCanceledEmail(
                    "kumalillankoon12@gmail.com",
                    orderId
                )
                withContext(Dispatchers.Main) {  // Update UI on main thread
                    if (invoiceSent) {
                        println("Invoice email sent successfully.")
                    } else {
                        println("Failed to send invoice email.")
                    }
                }
            }
        }

    }

    private fun updateButtonStates(currentStatus: String) {
        binding.dispatchBtn.isEnabled = currentStatus == "Processing"
        binding.deliveredBtn.isEnabled = currentStatus == "Dispatched"
        binding.cancelBtn.isEnabled = currentStatus !in listOf("Delivered", "Cancelled")
    }

    private fun showCancelConfirmationDialog(orderId: Int) {
        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
            .setPositiveButton("Yes") { _, _ ->
                if (buyerOrderViewModel.updateOrderStatus(orderId, "Cancelled")) {
                    updateButtonStates("Cancelled")
                }
            }
            .setNegativeButton("No", null)
            .show()

        // Set title and message dynamically if needed
        dialogBinding.dialogTitle.text = "Cancel Order"
        dialogBinding.dialogMessage.text = "Are you sure you want to cancel the order?"
    }



    override fun onResume() {
        super.onResume()
        val orderId = intent.getIntExtra("ORDER_ID",-1)
        model.loadOrderItems(orderId)
    }
}