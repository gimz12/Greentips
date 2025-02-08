package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.ExpenseDetails
import com.example.greentipskotlin.App.Model.SupplierPayment
import com.example.greentipskotlin.App.Model.SupplierPaymentDataProvider

class SupplierPaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val supplierPaymentDataProvider = SupplierPaymentDataProvider(application.applicationContext)

    private val _supplierPayments = MutableLiveData<List<SupplierPayment>>()
    val supplierPayments: LiveData<List<SupplierPayment>> get() = _supplierPayments

    private val _supplierPendingOrders = MutableLiveData<List<SupplierPayment>>()
    val supplierPendingOrders: LiveData<List<SupplierPayment>> get() = _supplierPendingOrders

    fun refreshPendingSupplierOrders() {
        val orderList = supplierPaymentDataProvider.getPartialPaidSupplierPayments()
        _supplierPendingOrders.value = orderList
    }

    fun insertSupplierOrder(supplierPayment: SupplierPayment) {
        supplierPaymentDataProvider.insertSupplierPayment(supplierPayment)
    }

    fun getPaymentsByStatus(status: String): List<SupplierPayment>{
        val payments = supplierPaymentDataProvider.getPaymentsByStatus(status)
        return payments
    }

    fun getPartialPaidSupplierPayments(): List<SupplierPayment> {
        return supplierPaymentDataProvider.getPartialPaidSupplierPayments()
    }

    fun updateSupplierPayment(
        paymentId: Int,
        newPaymentDate: String,
        newPaymentTime: String,
        newPaidAmount: Double,
        newRemainAmount: Double,
        newPaymentStatus: String
    ): Int {
        return supplierPaymentDataProvider.updateSupplierPayment(paymentId,newPaymentDate,newPaymentTime,newPaidAmount,newRemainAmount,newPaymentStatus)
    }

    fun getPaymentStatus(orderId: Int): String? {
        return supplierPaymentDataProvider.getPaymentStatus(orderId)
    }

    fun getSupplierPaymentByOrderId(orderId: Int): SupplierPayment? {
        return supplierPaymentDataProvider.getSupplierPaymentByOrderId(orderId)
    }

    fun getExpensesReport(startDate: String, endDate: String): List<ExpenseDetails> {
        return supplierPaymentDataProvider.getExpensesReport(startDate,endDate)
    }


}