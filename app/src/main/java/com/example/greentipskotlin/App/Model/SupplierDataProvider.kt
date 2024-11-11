package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class SupplierDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)


    fun insertSupplier(supplier: Supplier){
        greentipsDatabaseHelper.insertSupplier(supplier)
    }

    fun getAllSuppliers():List<Supplier>{
        return greentipsDatabaseHelper.getAllSupplier()
    }

}