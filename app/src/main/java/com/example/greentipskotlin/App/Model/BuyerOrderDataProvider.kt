package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import OrderDetails
import android.content.Context

class BuyerOrderDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun placeOrders(buyerOrder: BuyerOrder): Long {
        return greentipsDatabaseHelper.placeOrder(buyerOrder)
    }

    fun getBuyerOrdersByUserId(userId: Int): List<BuyerOrder> {
        return greentipsDatabaseHelper.getBuyerOrdersByUserId(userId)
    }

    fun getAllBuyerPendingOrders(): List<BuyerOrder> {
        return greentipsDatabaseHelper.getAllBuyerProcessingOrder()
    }

    fun updateOrderStatus(orderId: Int, newStatus: String): Boolean {
        return greentipsDatabaseHelper.updateOrderStatus(orderId, newStatus)
    }

    fun getOrderStatus(orderId: Int): String? {
        return greentipsDatabaseHelper.getOrderStatus(orderId)
    }

    fun getRevenueReport(startDate: String, endDate: String): Double {
        return greentipsDatabaseHelper.getRevenueReport(startDate, endDate)
    }

    fun getOrderDetails(startDate: String, endDate: String): List<OrderDetails> {
        return greentipsDatabaseHelper.getOrderDetails(startDate, endDate)
    }

    fun getBuyerOrderById(orderId: Int): BuyerOrder? {
        return greentipsDatabaseHelper.getBuyerOrderById(orderId)
    }

}
