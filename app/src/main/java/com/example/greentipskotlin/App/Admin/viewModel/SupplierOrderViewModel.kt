package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.SupplierOrderDataProvider
import kotlinx.coroutines.launch

class SupplierOrderViewModel(application: Application) : AndroidViewModel(application) {

    private val supplierOrderDataProvider = SupplierOrderDataProvider(application.applicationContext)

    private val _supplierOrders = MutableLiveData<List<SupplierOrder>>()
    val supplierOrders: LiveData<List<SupplierOrder>> get() = _supplierOrders

    private val _supplierPendingOrders = MutableLiveData<List<SupplierOrder>>()
    val supplierPendingOrders: LiveData<List<SupplierOrder>> get() = _supplierPendingOrders

    private val _supplierOffersApprovedByFieldManager = MutableLiveData<List<SupplierOrder>>()
    val supplierOffersApprovedByFieldManager: LiveData<List<SupplierOrder>> get() = _supplierOffersApprovedByFieldManager

    private val _supplierOffersApprovedByCEO = MutableLiveData<List<SupplierOrder>>()
    val supplierOffersApprovedByCEO: LiveData<List<SupplierOrder>> get() = _supplierOffersApprovedByCEO

    private val _supplierApprovedOrders = MutableLiveData<List<SupplierOrder>>()
    val supplierApprovedOrders: LiveData<List<SupplierOrder>> get() = _supplierApprovedOrders

    private val _supplierOffersByUserId = MutableLiveData<List<SupplierOrder>>()
    val supplierOffersByUserId: LiveData<List<SupplierOrder>> get() = _supplierOffersByUserId

    fun insertSupplierOrder(supplierOrder: SupplierOrder) {
        supplierOrderDataProvider.insertSupplierOrder(supplierOrder)
    }

    fun refreshData() {
        val orderList = supplierOrderDataProvider.getAllOrders()
        _supplierOrders.value = orderList
    }

    fun refreshPendingOrders() {
        val orderList = supplierOrderDataProvider.getUnapprovedOrders()
        _supplierPendingOrders.value = orderList
    }

    fun refreshFieldManagerApprovedOrders() {
        val orderList = supplierOrderDataProvider.getFieldManagerApprovedOrders()
        _supplierOffersApprovedByFieldManager.value = orderList
    }

    fun refreshCEOApprovedOrders() {
        val approvedOrders = supplierOrderDataProvider.getCeoApprovedOrders()
        _supplierOffersApprovedByCEO.value = approvedOrders
    }

    fun refreshApprovedOrders() {
        val approvedOrders = supplierOrderDataProvider.getApprovedSupplierOrders()
        _supplierApprovedOrders.value = approvedOrders
    }

    fun getSupplierOrdersByUserId(userId: Int) {
        val orders = supplierOrderDataProvider.getSupplierOrdersByUserId(userId)
        _supplierOffersByUserId.postValue(orders) // Post the data to LiveData
    }

    fun getPendingApproval() {
        val pendingOrders = supplierOrderDataProvider.getUnapprovedOrders()
        _supplierPendingOrders.value = pendingOrders
    }

    fun updateFieldManagerStatus(orderId: Int?, status: String): Boolean {
        val result = supplierOrderDataProvider.updateFieldManagerStatus(orderId, status)
        refreshPendingOrders() // Refresh data after update
        return result
    }

    fun updateCeoStatus(orderId: Int?, status: String): Boolean {
        val result = supplierOrderDataProvider.updateCeoStatus(orderId, status)
        refreshFieldManagerApprovedOrders() // Refresh data after update
        return result
    }

    fun updateSupplierOrder(supplierOrder: SupplierOrder) {
        supplierOrderDataProvider.updateSupplierOrder(supplierOrder)
    }

    fun deleteSupplierOrder(orderId: Int) {
        supplierOrderDataProvider.deleteSupplierOrder(orderId)
    }

    fun getPendingSupplierOrdersCount(): Int {
        return supplierOrderDataProvider.getPendingSupplierOrdersCount()
    }
}
