package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.Supplier
import com.example.greentipskotlin.App.Model.SupplierDataProvider

class SupplierViewModel(application: Application):AndroidViewModel(application) {

    private val supplierDataProvider = SupplierDataProvider(application.applicationContext)

    private val _suppliers = MutableLiveData<List<Supplier>>()
    val suppliers: LiveData<List<Supplier>> get()=_suppliers

    init {
        refreshData()
    }

    fun refreshData(){
        _suppliers.value = supplierDataProvider.getAllSuppliers()
    }

    fun insertSupplier(supplier: Supplier){
        supplierDataProvider.insertSupplier(supplier)
    }

}