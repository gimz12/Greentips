package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CeoDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertCEODetails(ceo: Ceo){
        greentipsDatabaseHelper.insertCEODetails(ceo)
    }

    fun getCEOById(id: Int): Ceo?{
        return greentipsDatabaseHelper.getCEOById(id)
    }

    fun updateCeo(ceo: Ceo): Int {
        return greentipsDatabaseHelper.updateCEO(ceo)
    }

}