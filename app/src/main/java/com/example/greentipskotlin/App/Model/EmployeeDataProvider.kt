package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EmployeeDataProvider (context: Context){

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insetEmployee(employee: Employee){
        greentipsDatabaseHelper.insertEmployee(employee)
    }

    fun getAllEmployees(): List<Employee>{
        return greentipsDatabaseHelper.getAllEmployees()
    }

    fun validateUser(username: String, password: String): LiveData<Int?> {
        val liveData = MutableLiveData<Int?>()
        val cursor = greentipsDatabaseHelper.validateUser(username, password)

        if (cursor != null && cursor.moveToFirst()) {
            val positionId = cursor.getInt(cursor.getColumnIndexOrThrow("employeePositionId"))
            liveData.postValue(positionId) // Post the position ID
        } else {
            liveData.postValue(null) // No match found
        }
        cursor?.close()
        return liveData
    }

    fun getLoggedInUserDetails(username: String, password: String): Employee? {
        return greentipsDatabaseHelper.getLoggedInUserDetails(username, password)
    }

    fun updateEmployee(employee: Employee): Int {
        return greentipsDatabaseHelper.updateEmployee(employee)
    }


    fun getEmployeeById(id: Int): Employee?{
        return greentipsDatabaseHelper.getEmployeeById(id)
    }


}