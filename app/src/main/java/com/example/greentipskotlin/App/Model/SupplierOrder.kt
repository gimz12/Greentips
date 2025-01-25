package com.example.greentipskotlin.App.Model

data class SupplierOrder(
    val ORDER_ID:Int? = null,
    val USER_ID:Int?,
    val ITEM_NAME:String,
    val ITEM_QUANTITY:Int,
    val ITEM_DESCRIPTION:String,
    val ESTIMATE_DELIVERY_DATE:String?,
    val QUANTITY_PRICE:Double?,
    val TOTAL_AMOUNT:Double,
    val FIELDMANAGER_STATUS:String,
    val CEO_STATUS:String,
)
