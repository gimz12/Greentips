package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.App.Model.ResourcesDataProvider

class ResourcesViewModel(application: Application):AndroidViewModel(application) {

    private val resourcesDataProvider = ResourcesDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)

    private val _resources = MutableLiveData<List<Resources>>()
    val resources: LiveData<List<Resources>> get() = _resources

    init {
        refreshData()
    }

    fun refreshData(){
        _resources.value = resourcesDataProvider.getAllResources()
    }

    fun insertResources(resources: Resources){
        resourcesDataProvider.insertResources(resources)
    }

    fun getAllEstateNames(): List<String>{
        return estateDataProvider.getAllEstates().map { it.estateName }
    }

    fun updateResource(resource: Resources) {
        resourcesDataProvider.updateResource(resource)
        refreshData() // Refresh list to show updated data
    }

    fun deleteResource(resourceId: Int) {
        resourcesDataProvider.deleteResource(resourceId)
        refreshData() // Refresh list to show updated data
    }


}