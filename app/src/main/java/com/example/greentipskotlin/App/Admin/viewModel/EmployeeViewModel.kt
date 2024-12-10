package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.EmployeeDataProvider
import com.example.greentipskotlin.App.Model.EmployeePosition
import com.example.greentipskotlin.App.Model.EmployeePositionDataProvider
import com.example.greentipskotlin.App.Model.Intercrops

class EmployeeViewModel (application: Application) : AndroidViewModel(application)  {

    private val employeePostionData = MutableLiveData<EmployeePosition>()
    private val employeeDataProvider = EmployeeDataProvider(application.applicationContext)
    private val employeePositionDataProvider = EmployeePositionDataProvider(application.applicationContext)

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> get() = _employees

    init {
        refreshData() // Load initial data
    }

    fun refreshData() {
        _employees.value = employeeDataProvider.getAllEmployees()
    }

    fun insertEmployeePosition(employeePosition: EmployeePosition) {
        employeePositionDataProvider.insertEmployeePosition(employeePosition)
    }

    fun insertEmployee(employee: Employee){
        employeeDataProvider.insetEmployee(employee)
    }

    fun validateUser(username: String, password: String): LiveData<Int?> {
        return employeeDataProvider.validateUser(username, password)
    }
}