package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Receipt
import com.example.greentipskotlin.App.Model.ReceiptDataProvider

class ReceiptViewModel(application: Application) : AndroidViewModel(application) {

    private val receiptDataProvider=ReceiptDataProvider(application.applicationContext)

    private val _receipts = MutableLiveData<List<Receipt>>()
    val receipts: LiveData<List<Receipt>> get() = _receipts

    fun insertReceipt(receipt: Receipt): Long {
        return receiptDataProvider.insertReceipt(receipt)
    }

    fun refreshData() {
        _receipts.value = receiptDataProvider.getAllReceipts()
    }

}