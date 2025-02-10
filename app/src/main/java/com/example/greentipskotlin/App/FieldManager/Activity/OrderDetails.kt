package com.example.greentipskotlin.App.FieldManager.Activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.OrderItemViewModel
import com.example.greentipskotlin.databinding.ActivityOrderDetailsBinding
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetails : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailsBinding
    private val model: OrderItemViewModel by viewModels()
    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()
    private lateinit var orderItemAdapter: BuyerOrderItemAdapter

    // Member variable to track the current order status
    private var currentStatus: String = "Processing"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getIntExtra("ORDER_ID", -1)
        val orderCost = intent.getDoubleExtra("ORDER_COST", 0.0)
        val orderDate = intent.getStringExtra("ORDER_DATE")
        val orderShippingAddress = intent.getStringExtra("ORDER_SHIPPING_ADDRESS")

        // Initialize currentStatus using your ViewModel (if available) or default to "Processing"
        currentStatus = buyerOrderViewModel.getOrderStatus(orderId) ?: "Processing"
        updateButtonStates(currentStatus)

        // Set initial data on the UI
        binding.orderIdTv.text = "Order ID: $orderId"
        binding.orderCostTv.text = "Order Cost: $orderCost"
        binding.orderDateTv.text = "Order Date: $orderDate"
        binding.orderStatusTv.text = "Order Status: $currentStatus"
        binding.orderShippingAddressTv.text = "Shipping Address: $orderShippingAddress"

        orderItemAdapter = BuyerOrderItemAdapter(emptyList())
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = orderItemAdapter

        // Observe order items changes
        model.orderItems.observe(this, { items ->
            orderItemAdapter.updateList(items)
        })

        // Observe order status changes and update the UI and button states accordingly
        buyerOrderViewModel.orderStatus.observe(this, { status ->
            currentStatus = status
            binding.orderStatusTv.text = "Order Status: $status"
            updateButtonStates(status)
        })

        // Load the current order status
        buyerOrderViewModel.loadOrderStatus(orderId)

        // When the Dispatch button is clicked, update the order status to "Dispatched"
        binding.dispatchBtn.setOnClickListener {
            // No need to check the status here since the button is enabled only when status == "Processing"
            if (buyerOrderViewModel.updateOrderStatus(orderId, "Dispatched")) {
                updateButtonStates("Dispatched")
                CoroutineScope(Dispatchers.IO).launch {
                    val invoiceSent = EmailHelper.sendOrderDispatchedEmail(
                        "kumalillankoon12@gmail.com",
                        orderId
                    )
                    withContext(Dispatchers.Main) {
                        if (invoiceSent) {
                            println("Invoice email sent successfully.")
                        } else {
                            println("Failed to send invoice email.")
                        }
                    }
                }
            }
        }

        // When the Delivered button is clicked, update the order status to "Delivered"
        binding.deliveredBtn.setOnClickListener {
            // Button is clickable only when status == "Dispatched"
            if (buyerOrderViewModel.updateOrderStatus(orderId, "Delivered")) {
                updateButtonStates("Delivered")
                CoroutineScope(Dispatchers.IO).launch {
                    val invoiceSent = EmailHelper.sendOrderDeliveredEmail(
                        "kumalillankoon12@gmail.com",
                        orderId
                    )
                    withContext(Dispatchers.Main) {
                        if (invoiceSent) {
                            println("Invoice email sent successfully.")
                        } else {
                            println("Failed to send invoice email.")
                        }
                    }
                }
            }
        }

        // When the Cancel button is clicked, show a confirmation dialog and update status if confirmed
        binding.cancelBtn.setOnClickListener {
            // The button is enabled only when the order is not yet Delivered or Cancelled.
            if (currentStatus !in listOf("Delivered", "Cancelled")) {
                showCancelConfirmationDialog(orderId)
                CoroutineScope(Dispatchers.IO).launch {
                    val invoiceSent = EmailHelper.sendOrderCanceledEmail(
                        "kumalillankoon12@gmail.com",
                        orderId
                    )
                    withContext(Dispatchers.Main) {
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

    /**
     * Updates the enabled state of the buttons based on the current order status.
     */
    private fun updateButtonStates(currentStatus: String) {
        binding.dispatchBtn.isEnabled = currentStatus == "Processing"
        binding.deliveredBtn.isEnabled = currentStatus == "Dispatched"
        binding.cancelBtn.isEnabled = currentStatus !in listOf("Delivered", "Cancelled")
    }

    /**
     * Displays a confirmation dialog for order cancellation.
     */
    private fun showCancelConfirmationDialog(orderId: Int) {
        val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setPositiveButton("Yes") { _, _ ->
                if (buyerOrderViewModel.updateOrderStatus(orderId, "Cancelled")) {
                    updateButtonStates("Cancelled")
                }
            }
            .setNegativeButton("No", null)
            .show()

        // Set the title and message for the dialog
        dialogBinding.dialogTitle.text = "Cancel Order"
        dialogBinding.dialogMessage.text = "Are you sure you want to cancel the order?"
    }

    override fun onResume() {
        super.onResume()
        val orderId = intent.getIntExtra("ORDER_ID", -1)
        model.loadOrderItems(orderId)
    }
}
