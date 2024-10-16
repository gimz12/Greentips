package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class EmployeePositionDataProvider(context: Context) {

    private val greentipsDBHelper = GreentipsDatabaseHelper(context)

    fun insertEmployeePosition(employeePosition: EmployeePosition){
        greentipsDBHelper.insertEmployeePosition(employeePosition)
    }

}