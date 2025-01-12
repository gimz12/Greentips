package com.example.greentipskotlin.App.Model

data class OrderItem(
    val ORDER_ITEM_ID:Int? = null,
    val ORDER_ITEM_ORDER_ID:Int?,
    val ORDER_ITEM_NAME:String,
    val ORDER_ITEM_QUANTITY: Int,
    val ORDER_ITEM_PRICE:Double,
    val ORDER_ITEM_TOTAL_PRICE: Double
)
