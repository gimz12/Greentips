package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class IntercropsDataProvider (context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertInterCrops(intercrops: Intercrops){
        greentipsDatabaseHelper.insertInterCrops(intercrops)
    }

    fun getAllInterCrops():List<Intercrops>{
        return greentipsDatabaseHelper.getAllIntercrops()
    }


}