package com.example.greentipskotlin.App.Model

data class Invoice(
    val invoiceId:Int? =null,
    val orderId: Int?,
    val paymentId: Int?,
    val date: String,
    val time: String,
    val subtotal: Double,
    val total: Double
)
