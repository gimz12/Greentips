package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class FertilizerDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertFertilizer(fertilizer: Fertilizer){
        greentipsDatabaseHelper.insertFertilizer(fertilizer)
    }

    fun getAllFertilizer():List<Fertilizer>{
        return greentipsDatabaseHelper.getAllFertilizer()
    }

}