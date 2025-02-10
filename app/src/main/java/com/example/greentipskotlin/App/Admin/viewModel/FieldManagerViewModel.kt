package com.example.greentipskotlin.App.Admin.viewModel

import GreentipsDatabaseHelper
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class FieldManagerViewModel(application: Application): AndroidViewModel(application) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(application.applicationContext)


    fun getEstateIdByEmployeeId(employeeId: Int): Int? {
        return greentipsDatabaseHelper.getEstateIdByEmployeeId(employeeId)
    }

}