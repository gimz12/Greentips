package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.databinding.ActivityEmployeeDetailsBinding

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailsBinding
    private val model: EmployeeViewModel by viewModels()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get employee ID from Intent
        employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)

        // Check for invalid employee ID
        if (employeeId == -1) {
            Toast.makeText(this, "Invalid Employee ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch employee details once and populate fields
        val existingEmployee = model.getEmployeeById(employeeId)
        existingEmployee?.let {
            populateEmployeeDetails(it)
        }

        // Set save button click listener
        binding.saveEmployeeButton.setOnClickListener {
            saveUpdatedEmployeeDetails(existingEmployee)
        }
    }

    /**
     * Populate the EditText fields with the employee's details.
     */
    private fun populateEmployeeDetails(employee: Employee) {
        binding.employeeNameEditText.setText(employee.employeeName)
        binding.employeePositionEditText.setText(employee.employeePositionId?.toString() ?: "")
        binding.employeePhoneEditText.setText(employee.phoneNumber)
        binding.employeeEmailEditText.setText(employee.email)
    }

    /**
     * Gather updated details, merge with existing data, and save to the database.
     */
    private fun saveUpdatedEmployeeDetails(existingEmployee: Employee?) {
        if (existingEmployee == null) {
            Toast.makeText(this, "Employee data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedEmployee = existingEmployee.copy(
            employeeName = binding.employeeNameEditText.text.toString().ifBlank { existingEmployee.employeeName },
            employeePositionId = binding.employeePositionEditText.text.toString().toIntOrNull()
                ?: existingEmployee.employeePositionId,
            phoneNumber = binding.employeePhoneEditText.text.toString().ifBlank { existingEmployee.phoneNumber },
            email = binding.employeeEmailEditText.text.toString().ifBlank { existingEmployee.email }
        )

        // Update employee using ViewModel
        val rowsAffected = model.updateEmployee(updatedEmployee)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Employee updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update employee", Toast.LENGTH_SHORT).show()
        }
    }
}
