package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.Fertilizer
import com.example.greentipskotlin.App.Model.FertilizerDataProvider

class FertilizerViewModel(application: Application):AndroidViewModel(application) {

    private val fertilizerDataProvider = FertilizerDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)

    private val _fertilizer = MutableLiveData<List<Fertilizer>>()
    val fertilizer: LiveData<List<Fertilizer>> get()=_fertilizer

    init {
        refreshData()
    }

    fun insertFertilizer(fertilizer: Fertilizer){
        fertilizerDataProvider.insertFertilizer(fertilizer)
    }

    fun refreshData(){
        _fertilizer.value = fertilizerDataProvider.getAllFertilizer()
    }

    fun getAllEstateNames(): List<String> {
        return estateDataProvider.getAllEstates().map { it.estateName }
    }

}