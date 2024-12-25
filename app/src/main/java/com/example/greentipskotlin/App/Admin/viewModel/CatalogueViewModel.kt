package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.App.Model.CatalogueDataProvider

class CatalogueViewModel(application: Application):AndroidViewModel(application) {

    private val catalogueDataProvider = CatalogueDataProvider(application.applicationContext)

    private val _catalogItems = MutableLiveData<List<Catalogue>>()
    val catalogItems: LiveData<List<Catalogue>> get()=_catalogItems

    fun insertBuyer(catalogue: Catalogue){
        catalogueDataProvider.insertCatalogueItem(catalogue)
    }

}