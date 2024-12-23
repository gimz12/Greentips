package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.App.Model.AdminDataProvider
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.App.Model.CeoDataProvider
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.EmployeeDataProvider
import com.example.greentipskotlin.App.Model.EmployeePosition
import com.example.greentipskotlin.App.Model.EmployeePositionDataProvider
import com.example.greentipskotlin.App.Model.EstateDataProvider
import com.example.greentipskotlin.App.Model.FieldManager
import com.example.greentipskotlin.App.Model.FieldManagerDataProvider
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.App.Model.Resources

class EmployeeViewModel (application: Application) : AndroidViewModel(application)  {

    private val employeePostionData = MutableLiveData<EmployeePosition>()
    private val employeeDataProvider = EmployeeDataProvider(application.applicationContext)
    private val employeePositionDataProvider = EmployeePositionDataProvider(application.applicationContext)
    private val adminDataProvider = AdminDataProvider(application.applicationContext)
    private val ceoDataProvider = CeoDataProvider(application.applicationContext)
    private val fieldManagerDataProvider = FieldManagerDataProvider(application.applicationContext)
    private val estateDataProvider = EstateDataProvider(application.applicationContext)

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

    fun getLoggedInUserDetails(username: String, password: String): Employee? {
        return employeeDataProvider.getLoggedInUserDetails(username, password)
    }

    fun insertAdminDetails(admin: Admin){
        adminDataProvider.insertAdmin(admin)
    }

    // In the ViewModel class
    fun getEmployeeIdByUsername(username: String): LiveData<Int?> {
        return adminDataProvider.getEmployeeIdByUsername(username)
    }

    fun updateEmployee(employee: Employee): Int {
        val rowsAffected = employeeDataProvider.updateEmployee(employee)
        refreshData() // Refresh list to show updated data
        return rowsAffected
    }

    fun updateAdmin(admin: Admin): Int {
        val rowsAffected = adminDataProvider.updateAdmin(admin)
        refreshData() // Refresh list to show updated data
        return rowsAffected
    }


    fun getEmployeeById(id: Int): Employee?{
        return employeeDataProvider.getEmployeeById(id)
    }

    fun getAdminById(id: Int): Admin?{
        return adminDataProvider.getAdminById(id)
    }

    //CEO

    fun insertCEODetails(ceo: Ceo){
        ceoDataProvider.insertCEODetails(ceo)
    }

    fun getCEOById(id: Int): Ceo?{
        return ceoDataProvider.getCEOById(id)
    }

    fun updateCEO(ceo: Ceo): Int {
        val rowsAffected = ceoDataProvider.updateCeo(ceo)
        refreshData()
        return rowsAffected
    }

    //Field Manager

    fun insertFieldManager(fieldManager: FieldManager){
        fieldManagerDataProvider.insertFieldManager(fieldManager)
    }

    fun getFieldManagerById(id: Int): FieldManager?{
        return fieldManagerDataProvider.getFieldManagerById(id)
    }

    fun updateFieldManager(fieldManager: FieldManager): Int {
        val rowsAffected = fieldManagerDataProvider.updateFieldManager(fieldManager)
        refreshData()
        return rowsAffected
    }

    fun getAllEstateNames(): List<String> {
        return estateDataProvider.getAllEstates().map { it.estateName }
    }





}