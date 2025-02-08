package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.EstateDataProvider

class EstateViewModel(application: Application) : AndroidViewModel(application) {

    private val estateDataProvider = EstateDataProvider(application.applicationContext)

    private val _estates = MutableLiveData<List<Estate>>()
    val estates: LiveData<List<Estate>>
        get() = _estates

    init {
        // Initialize with the current list of estates.
        _estates.value = estateDataProvider.getAllEstates()
    }

    fun insertEstate(estate: Estate) {
        estateDataProvider.insertEstate(estate)
        refreshData()
    }

    // Call this method to update the LiveData when needed.
    fun refreshData() {
        _estates.value = estateDataProvider.getAllEstates()
        Log.d("EstateViewModel", "Number of estates: ${_estates.value?.size ?: 0}")
    }
}
