package com.example.greentipskotlin.App.Model

data class BuyerOrder(
    val ORDER_ID:Int? = null,
    val USER_ID:Int?,
    val ORDER_COST:Double,
    val ORDER_DATE: String,
    val ORDER_STATUS:String,
    val ORDER_SHIPPING_ADDRESS: String
)
