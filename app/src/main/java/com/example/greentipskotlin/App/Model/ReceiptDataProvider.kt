package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class ReceiptDataProvider(context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertReceipt(receipt: Receipt): Long {
        return greentipsDatabaseHelper.insertReceipt(receipt)
    }

    fun getAllReceipts(): List<Receipt> {
        return greentipsDatabaseHelper.getAllReceipts()
    }

}