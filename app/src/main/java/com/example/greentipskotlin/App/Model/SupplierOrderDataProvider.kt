package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class SupplierOrderDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertSupplierOrder(supplierOrder: SupplierOrder) {
        greentipsDatabaseHelper.insertSupplierOrder(supplierOrder)
    }

    fun getFieldManagerApprovedOrders(): List<SupplierOrder> {
        return greentipsDatabaseHelper.getFieldManagerApprovedSupplierOrders()
    }

    fun getCeoApprovedOrders(): List<SupplierOrder> {
        return greentipsDatabaseHelper.getCeoApprovedSupplierOrders()
    }

    fun getUnapprovedOrders(): List<SupplierOrder> {
        return greentipsDatabaseHelper.getUnapprovedSupplierOrders()
    }

    fun getAllOrders(): List<SupplierOrder> {
        return greentipsDatabaseHelper.getSupplierOrders()
    }

    fun updateFieldManagerStatus(orderId: Int?, status: String): Boolean {
        return greentipsDatabaseHelper.updateFieldManagerStatus(orderId, status)
    }

    fun updateCeoStatus(orderId: Int?, status: String): Boolean {
        return greentipsDatabaseHelper.updateCeoStatus(orderId, status)
    }

    fun getSupplierOrdersByUserId(userId: Int): List<SupplierOrder> {
        return greentipsDatabaseHelper.getSupplierOrdersByUserId(userId)
    }

    fun updateSupplierOrder(supplierOrder: SupplierOrder) {
        greentipsDatabaseHelper.updateSupplierOrder(supplierOrder)
    }

    fun deleteSupplierOrder(orderId: Int) {
        greentipsDatabaseHelper.deleteSupplierOrder(orderId)
    }

    fun getApprovedSupplierOrders(): List<SupplierOrder> {
        return greentipsDatabaseHelper.getApprovedSupplierOrders()
    }

    fun getPendingSupplierOrdersCount(): Int {
        return greentipsDatabaseHelper.getPendingSupplierOrdersCount()
    }


}