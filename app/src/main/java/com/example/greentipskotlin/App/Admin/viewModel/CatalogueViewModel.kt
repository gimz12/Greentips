package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.App.Model.CatalogueDataProvider
import com.example.greentipskotlin.App.Model.Worker

class CatalogueViewModel(application: Application):AndroidViewModel(application) {

    private val catalogueDataProvider = CatalogueDataProvider(application.applicationContext)

    private val _catalogItems = MutableLiveData<List<Catalogue>>()
    val catalogItems: LiveData<List<Catalogue>> get()=_catalogItems

    fun insertCatalogue(catalogue: Catalogue){
        catalogueDataProvider.insertCatalogueItem(catalogue)
    }

    fun refreshData(){
        _catalogItems.value = catalogueDataProvider.getAllBuyerCatalogueItems()
    }

    fun refreshDataFieldManager(){
        _catalogItems.value = catalogueDataProvider.getAllCatalogueItems()
    }

    fun getItemByCatalogueId(id :Int): Catalogue?{
        return catalogueDataProvider.getItemByCatalogueId(id)
    }

    fun updateCatalogueItem(catalogue: Catalogue): Int {
        return catalogueDataProvider.updateCatalogueItem(catalogue)
    }

}