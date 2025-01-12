package com.example.greentipskotlin.App.Model

data class CreditCard(
    val cardId: Int = 0,
    val userId: Int,
    val cardNumber: String,
    val expiryDate: String,
    val cardHolderName: String
)

