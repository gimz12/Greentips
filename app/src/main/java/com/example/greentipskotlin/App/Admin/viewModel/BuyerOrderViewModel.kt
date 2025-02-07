package com.example.greentipskotlin.App.Admin.viewModel

import OrderDetails
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.BuyerOrderDataProvider
import com.example.greentipskotlin.App.Model.OrderItemReport

class BuyerOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val buyerOrderDataProvider = BuyerOrderDataProvider(application.applicationContext)

    private val _buyerOrders = MutableLiveData<List<BuyerOrder>>()
    val buyerOrders: LiveData<List<BuyerOrder>> get() = _buyerOrders

    private val _orderStatus = MutableLiveData<String>()
    val orderStatus: LiveData<String> get() = _orderStatus

    fun placeOrders(buyerOrder: BuyerOrder): Long {
        return buyerOrderDataProvider.placeOrders(buyerOrder)
    }

    fun refreshData(userId: Int) {
        val orderList = buyerOrderDataProvider.getBuyerOrdersByUserId(userId)
        _buyerOrders.value = orderList
    }

    fun refreshAllOrders() {
        val orderList = buyerOrderDataProvider.getAllBuyerPendingOrders()
        _buyerOrders.value = orderList
    }

    fun getOrderStatus(orderId: Int): String? {
        return buyerOrderDataProvider.getOrderStatus(orderId)
    }

    fun updateOrderStatus(orderId: Int, newStatus: String): Boolean {
        val success = buyerOrderDataProvider.updateOrderStatus(orderId, newStatus)
        if (success) {
            _orderStatus.value = newStatus
        }
        return success
    }

    fun loadOrderStatus(orderId: Int) {
        _orderStatus.value = buyerOrderDataProvider.getOrderStatus(orderId) ?: "Processing"
    }

    fun getRevenueReport(startDate: String, endDate: String): Double {
        return buyerOrderDataProvider.getRevenueReport(startDate, endDate)
    }

    fun getOrderDetails(startDate: String, endDate: String): List<OrderDetails> {
        return buyerOrderDataProvider.getOrderDetails(startDate, endDate)
    }
}
