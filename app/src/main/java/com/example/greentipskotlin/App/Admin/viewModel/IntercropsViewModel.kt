package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.App.Model.IntercropsDataProvider

class IntercropsViewModel(application: Application) : AndroidViewModel(application) {

    private val intercropsDataProvider = IntercropsDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)

    private val _intercrops = MutableLiveData<List<Intercrops>>()
    val intercrops: LiveData<List<Intercrops>> get() = _intercrops

    init {
        refreshData() // Load initial data
    }

    fun refreshData() {
        _intercrops.value = intercropsDataProvider.getAllInterCrops()
    }

    fun insertInterCrops(intercrop: Intercrops) {
        intercropsDataProvider.insertInterCrops(intercrop)
        refreshData() // Refresh the list after insertion
    }

    fun getAllEstateNames(): List<String> {
        return estateDataProvider.getAllEstates().map { it.estateName }
    }
}
