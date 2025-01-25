package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.SupplierOrderDataProvider

class SupplierOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val supplierOrderDataProvider = SupplierOrderDataProvider(application.applicationContext)

    private val _supplierOrders = MutableLiveData<List<SupplierOrder>>()
    val supplierOrders: LiveData<List<SupplierOrder>> get() = _supplierOrders

    fun insertSupplierOrder(supplierOrder: SupplierOrder){
        return supplierOrderDataProvider.insertSupplierOrder(supplierOrder)
    }

    fun refreshData() {
        val orderList = supplierOrderDataProvider.getAllOrders()
        _supplierOrders.value = orderList
    }

    fun getApprovedByFieldManager() {
        val approvedOrders = supplierOrderDataProvider.getFieldManagerApprovedOrders()
        _supplierOrders.value = approvedOrders
    }

    fun getApprovedByCeo() {
        val approvedOrders = supplierOrderDataProvider.getCeoApprovedOrders()
        _supplierOrders.value = approvedOrders
    }

    fun getPendingApproval() {
        val pendingOrders = supplierOrderDataProvider.getUnapprovedOrders()
        _supplierOrders.value = pendingOrders
    }
}
