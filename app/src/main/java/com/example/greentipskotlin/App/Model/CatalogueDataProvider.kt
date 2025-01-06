package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CatalogueDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertCatalogueItem(catalogue: Catalogue){
        greentipsDatabaseHelper.insertCatalogueItem(catalogue)
    }

    fun getAllBuyerCatalogueItems():List<Catalogue>{
        return greentipsDatabaseHelper.getAllBuyerCatalogueItems()
    }

    fun getAllCatalogueItems():List<Catalogue>{
        return greentipsDatabaseHelper.getAllCatalogueItems()
    }

    fun getItemByCatalogueId(id :Int):Catalogue?{
        return greentipsDatabaseHelper.getItemByCatalogueId(id)
    }

    fun updateCatalogueItem(catalogue: Catalogue): Int {
        return greentipsDatabaseHelper.updateCatalogueItem(catalogue)
    }

    fun updateCatalogueQuantity(itemName: String, quantitySold: Int, callback: (Boolean) -> Unit) {
        val isUpdated = greentipsDatabaseHelper.updateCatalogueQuantity(itemName, quantitySold)
        callback(isUpdated)
    }

    fun updateCatalogueQuantityRemove(itemName: String, quantitySold: Int, callback: (Boolean) -> Unit) {
        val isUpdated = greentipsDatabaseHelper.updateCatalogueQuantityRemove(itemName, quantitySold)
        callback(isUpdated)
    }

}