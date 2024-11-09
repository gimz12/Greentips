package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class HarvestInfoDataProvider (context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertHarvestInfo(harvestInfo: HarvestInfo){
        greentipsDatabaseHelper.insertHarvestInfo(harvestInfo)
    }

}