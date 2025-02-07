package com.example.greentipskotlin.App.Model

data class ExpenseDetail(
    val orderId: Int,
    val userName: String,
    val companyName: String,
    val itemName: String,
    val itemQuantity: Int,
    val quantityPrice: Double,
    val totalAmount: Double
)
