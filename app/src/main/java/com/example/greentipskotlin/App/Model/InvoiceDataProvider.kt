package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class InvoiceDataProvider(context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertInvoice(invoice: Invoice): Long {
        return greentipsDatabaseHelper.insertInvoice(invoice)
    }

    fun getAllInvoices(): List<Invoice> {
        return greentipsDatabaseHelper.getAllInvoices()
    }

}