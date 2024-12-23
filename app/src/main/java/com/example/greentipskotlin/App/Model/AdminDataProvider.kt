package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AdminDataProvider (context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertAdmin(admin: Admin){
        greentipsDatabaseHelper.insertAdminDetails(admin)
    }

    // In the DataProvider class
    fun getEmployeeIdByUsername(username: String): LiveData<Int?> {
        val result = MutableLiveData<Int?>()
        Thread {
            val employeeId = greentipsDatabaseHelper.getEmployeeIdByUsername(username)
            result.postValue(employeeId)
        }.start()
        return result
    }

    fun getAdminById(id: Int): Admin?{
        return greentipsDatabaseHelper.getAdminById(id)
    }

    fun updateAdmin(admin: Admin): Int {
        return greentipsDatabaseHelper.updateAdmin(admin)
    }



}