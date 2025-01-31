package com.example.greentipskotlin.App.Model

data class SupplierPayment(
    val PAYMENT_ID:Int? = null,
    val PAYMENT_ORDER_ID:Int?,
    val PAYMENT_USER_ID:Int?,
    val PAYMENT_DATE:String?,
    val PAYMENT_TIME:String?,
    val PAYMENT_TYPE:String?,
    val PAYMENT_STATUS:String?,
    val REMAIN_AMOUNT:Double?,
    val PAID_AMOUNT:Double?,
    val TOTAL_AMOUNT:Double?
)
