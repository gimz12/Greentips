package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.CoconutDataProvider
import com.example.greentipskotlin.App.Model.EstateDataProvider

class CoconutViewModel (application: Application):AndroidViewModel(application) {

    private val coconutDataProvider = CoconutDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)
    private val coconuts = coconutDataProvider.getAllCoconutTrees()

    fun insertCoconut(coconut: Coconut){
        coconutDataProvider.insertCoconuts(coconut)
    }

    fun getAllEstateNames(): List<String>{
        val names = estateDataProvider.getAllEstates();
        return names.map{it.estateName}
    }

    fun getAllCoconutTrees():List<Coconut>{
        Log.d("EmployeeRepository", "Number of employees: ${coconuts.size}")
        return coconuts
    }

}