package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.App.Model.OrderItemDataProvider

class OrderItemViewModel (application: Application) : AndroidViewModel(application) {

    private val orderItemDataProvider = OrderItemDataProvider(application.applicationContext)

    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> get() = _orderItems

    // Load order items for a specific orderId
    fun loadOrderItems(orderId: Int) {
        val items = orderItemDataProvider.getOrderItemsByOrderId(orderId)
        Log.d("OrderItems", "Loaded items: $items")
        _orderItems.postValue(items)
    }

    // Insert an order item
    fun insertOrderItem(orderItem: OrderItem) {
        orderItemDataProvider.insertOrderItem(orderItem)
    }

}