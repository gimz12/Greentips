package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CoconutDataProvider (context: Context){

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertCoconuts(coconut: Coconut){
        greentipsDatabaseHelper.insertCoconuts(coconut)
    }

    fun getAllCoconutTrees():List<Coconut>{
        return greentipsDatabaseHelper.getAllCoconuts()
    }

    fun getCoconutProductionReport(): List<CoconutProductionReport> {
        return greentipsDatabaseHelper.getCoconutProductionReport()
    }

    fun getCoconutTreeCount(): Int {
        return greentipsDatabaseHelper.getCoconutTreeCount()
    }

}