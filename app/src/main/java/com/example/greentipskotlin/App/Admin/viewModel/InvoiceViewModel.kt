package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Invoice
import com.example.greentipskotlin.App.Model.InvoiceDataProvider

class InvoiceViewModel(application: Application) : AndroidViewModel(application) {

    private val invoiceDataProvider = InvoiceDataProvider(application.applicationContext)

    private val _invoices = MutableLiveData<List<Invoice>>()
    val invoices: LiveData<List<Invoice>> get() = _invoices

    fun refreshData() {
        _invoices.value = invoiceDataProvider.getAllInvoices()
    }

    fun insertInvoice(invoice: Invoice): Long {
        return invoiceDataProvider.insertInvoice(invoice)
    }


}