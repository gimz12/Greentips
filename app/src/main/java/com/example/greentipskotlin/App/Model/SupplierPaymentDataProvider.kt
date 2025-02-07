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

    fun updateSupplierPayment(
        paymentId: Int,
        newPaymentDate: String,
        newPaymentTime: String,
        newPaidAmount: Double,
        newRemainAmount: Double,
        newPaymentStatus: String
    ): Int {
        return greentipsDatabaseHelper.updateSupplierPayment(paymentId,newPaymentDate,newPaymentTime,newPaidAmount,newRemainAmount,newPaymentStatus)
    }

    fun getPaymentStatus(orderId: Int): String? {
        return greentipsDatabaseHelper.getPaymentStatus(orderId)
    }

    fun getSupplierPaymentByOrderId(orderId: Int): SupplierPayment? {
        return greentipsDatabaseHelper.getSupplierPaymentByOrderId(orderId)
    }

    fun getExpensesReport(startDate: String, endDate: String): List<ExpenseDetail> {
        return greentipsDatabaseHelper.getExpensesReport(startDate,endDate)
    }

}