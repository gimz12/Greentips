package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class EmployeeDataProvider (context: Context){

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insetEmployee(employee: Employee){
        greentipsDatabaseHelper.insertEmployee(employee)
    }
}