package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class SupplierPaymentDataProvider(context: Context) {

    private val greentipsDatabaseHelper= GreentipsDatabaseHelper(context)


    fun insertSupplierPayment(supplierPayment: SupplierPayment){
        greentipsDatabaseHelper.insertSupplierPayment(supplierPayment)
    }

    fun getPaymentsByStatus(status: String): List<SupplierPayment>{
        return greentipsDatabaseHelper.getSupplierPaymentsByStatus(status)
    }

    fun getPartialPaidSupplierPayments(): List<SupplierPayment> {
        return greentipsDatabaseHelper.getPartialPaidSupplierPayments()
    }

}