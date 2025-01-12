package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class OrderItemDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertOrderItem(orderItem: OrderItem){
        greentipsDatabaseHelper.insertOrderItem(orderItem)
    }

    fun getOrderItemsByOrderId(orderId: Int): List<OrderItem> {
        return greentipsDatabaseHelper.getOrderItemsByOrderId(orderId)
    }

}