package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.App.Model.IntercropsDataProvider

class IntercropsViewModel (application: Application): AndroidViewModel(application) {

    private val intercropsDataProvider = IntercropsDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)
    private val intercrops = intercropsDataProvider.getAllInterCrops()

    fun insertInterCrops(intercrops: Intercrops){
        intercropsDataProvider.insertInterCrops(intercrops)
    }

    fun getAllIntercrop():List<Intercrops>{
        return intercrops
    }

    fun getAllEstateNames():List<String>{
        val names = estateDataProvider.getAllEstates()
        return names.map { it.estateName }
    }

}