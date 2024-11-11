package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.App.Model.HarvestInfoDataProvider
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.App.Model.IntercropsDataProvider

class HarvestInfoViewModel (application: Application): AndroidViewModel(application) {

    private val harvestInfoDataProvider = HarvestInfoDataProvider(application.applicationContext)
    private val intercropsDataProvider = IntercropsDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)

    private val _harvestInfos = MutableLiveData<List<HarvestInfo>>()
    val harvestInfos: LiveData<List<HarvestInfo>> get() = _harvestInfos

    init {
        refreshData()
    }

    fun refreshData(){
        _harvestInfos.value = harvestInfoDataProvider.getAllHarvestInfo()
    }

    fun insertHarvestInfo(harvestInfo: HarvestInfo){
        harvestInfoDataProvider.insertHarvestInfo(harvestInfo)
    }


    fun getAllEstates():List<Estate>{
        return estateDataProvider.getAllEstates()
    }

    fun getIntercropsForEstate(estateId: Int): List<Intercrops> {
        return intercropsDataProvider.getIntercropsForEstate(estateId)
    }

}