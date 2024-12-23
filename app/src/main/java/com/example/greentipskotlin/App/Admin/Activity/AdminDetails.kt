package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityAdminDetailsBinding

class AdminDetails : AppCompatActivity() {

    private lateinit var binding:ActivityAdminDetailsBinding
    private val model: EmployeeViewModel by viewModels()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)

        // Check for invalid employee ID
        if (employeeId == -1) {
            Toast.makeText(this, "Invalid Employee ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val existingAdmin = model.getAdminById(employeeId)
        existingAdmin?.let {
            populateAdminDetails(it)
        }

        binding.buttonUpdate.setOnClickListener {
            saveUpdatedEmployeeDetails(existingAdmin)
        }

    }

    //populate Admin
    fun populateAdminDetails(admin: Admin) {
        admin.EmployerId?.let { binding.usernameTextView.setText(it.toString()) }
        binding.shiftStartTime.setText(admin.ShiftStartTime)
        binding.shiftEndTime.setText(admin.ShiftEndTime)
    }


    private fun saveUpdatedEmployeeDetails(existingAdmin: Admin?) {
        if (existingAdmin == null) {
            Toast.makeText(this, "Employee data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedAdmin = existingAdmin.copy(
            ShiftStartTime = binding.shiftStartTime.text.toString().ifBlank { existingAdmin.ShiftStartTime },
            ShiftEndTime = binding.shiftEndTime.text.toString().ifBlank { existingAdmin.ShiftEndTime },
        )

        // Update employee using ViewModel
        val rowsAffected = model.updateAdmin(updatedAdmin)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Admin updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update Admin", Toast.LENGTH_SHORT).show()
        }
    }

}