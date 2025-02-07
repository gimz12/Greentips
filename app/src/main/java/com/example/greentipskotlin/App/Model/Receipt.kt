package com.example.greentipskotlin.App.Model

data class Receipt(
    val receiptId:Int? =null,
    val invoiceId: String,
    val date: String,
    val time: String
)
