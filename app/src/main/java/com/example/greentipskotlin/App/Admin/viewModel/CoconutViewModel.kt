package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.CoconutDataProvider
import com.example.greentipskotlin.App.Model.CoconutProductionReport
import com.example.greentipskotlin.App.Model.EstateDataProvider

class CoconutViewModel (application: Application):AndroidViewModel(application) {

    private val coconutDataProvider = CoconutDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)
    private val coconuts = coconutDataProvider.getAllCoconutTrees()

    private val _coconutTrees = MutableLiveData<List<Coconut>>()
    val coconutTrees: LiveData<List<Coconut>> get()=_coconutTrees

    init {
        refreshData()
    }

    fun insertCoconut(coconut: Coconut){
        coconutDataProvider.insertCoconuts(coconut)
    }

    fun getAllEstateNames(): List<String>{
        val names = estateDataProvider.getAllEstates();
        return names.map{it.estateName}
    }

    fun getAllCoconutTrees():List<Coconut>{
        Log.d("CoconutRepository", "Number of Coconuts: ${coconuts.size}")
        return coconuts
    }

    fun refreshData(){
        _coconutTrees.value = coconutDataProvider.getAllCoconutTrees()
    }

    fun getCoconutProductionReport(): List<CoconutProductionReport> {
        return coconutDataProvider.getCoconutProductionReport()
    }

}