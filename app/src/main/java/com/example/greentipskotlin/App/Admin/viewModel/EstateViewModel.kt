package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.EstateDataProvider

class EstateViewModel (application: Application) : AndroidViewModel(application)  {

    private val estateDataProvider = EstateDataProvider(application.applicationContext)
    private val estates=estateDataProvider.getAllEstates()

    fun insertEstate(estate: Estate){
        estateDataProvider.insertEstate(estate)
    }

    fun getAllEstates(): List<Estate> {
        Log.d("EmployeeRepository", "Number of employees: ${estates.size}")
        return estates
    }


}