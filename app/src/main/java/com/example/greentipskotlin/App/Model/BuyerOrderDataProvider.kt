package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class BuyerOrderDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun placeOrders(buyerOrder: BuyerOrder): Long{
        return greentipsDatabaseHelper.placeOrder(buyerOrder)
    }

    fun getBuyerOrdersByUserId(userId: Int): List<BuyerOrder> {
        return greentipsDatabaseHelper.getBuyerOrdersByUserId(userId)
    }

    fun getAllBuyerPendingOrders(): List<BuyerOrder>{
        return greentipsDatabaseHelper.getAllBuyerProcessingOrder()
    }

    fun updateOrderStatus(orderId: Int, newStatus: String): Boolean {
        return greentipsDatabaseHelper.updateOrderStatus(orderId,newStatus)
    }

    fun getOrderStatus(orderId: Int): String? {
        return greentipsDatabaseHelper.getOrderStatus(orderId)
    }



}