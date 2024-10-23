package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class EstateDataProvider (context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context);

    fun insertEstate(estate: Estate){
        greentipsDatabaseHelper.insertEstate(estate)
    }

    fun getAllEstates():List<Estate>{
        return greentipsDatabaseHelper.getAllEstates()
    }
}