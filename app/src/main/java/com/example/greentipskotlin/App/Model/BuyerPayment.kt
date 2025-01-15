package com.example.greentipskotlin.App.Model

data class BuyerPayment(
    val PAYMENT_ID:Int? = null,
    val PAYMENT_ORDER_ID:Int?,
    val PAYMENT_USER_ID:Int?,
    val PAYMENT_AMOUNT:Double,
    val PAYMENT_STATUS: String,
    val PAYMENT_METHOD:String,
    val PAYMENT_DATE_TIME: String
)
