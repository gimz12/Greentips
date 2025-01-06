package com.example.greentipskotlin.App.Model

data class Cart(
    val Cart_Id:Int? = null,
    val CART_USER_ID:Int?,
    val CART_ITEM_NAME:String,
    val CART_ITEM_PRICE: Double,
    val CART_ITEM_DATE:String,
    val CART_ITEM_QUANTITY: Int,
    val CART_ITEM_TOTAL_PRICE: Double
)
