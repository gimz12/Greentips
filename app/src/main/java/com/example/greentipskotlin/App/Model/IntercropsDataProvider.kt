package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class IntercropsDataProvider (context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertInterCrops(intercrops: Intercrops){
        greentipsDatabaseHelper.insertInterCrops(intercrops)
    }

    fun getIntercropsForEstate(estateId: Int): List<Intercrops> {
        return greentipsDatabaseHelper.getIntercropsForEstate(estateId)
    }


    fun getAllInterCrops():List<Intercrops>{
        return greentipsDatabaseHelper.getAllIntercrops()
    }

    fun getEstateWiseIntercropReport(): List<IntercropProductionReport> {
        return greentipsDatabaseHelper.getEstateWiseIntercropReport()
    }


}