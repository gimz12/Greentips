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
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityCeoDetailsBinding

class CeoDetails : AppCompatActivity() {

    private lateinit var binding:ActivityCeoDetailsBinding
    private val model:EmployeeViewModel by viewModels()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCeoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)

        // Check for invalid employee ID
        if (employeeId == -1) {
            Toast.makeText(this, "Invalid Employee ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val existingCeo = model.getCEOById(employeeId)
        existingCeo?.let {
            populateCeoDetails(it)
        }

        binding.buttonUpdate.setOnClickListener {
            saveUpdatedEmployeeDetails(existingCeo)
        }

    }

    //populate Admin
    fun populateCeoDetails(ceo: Ceo) {
        ceo.EmployerId?.let { binding.usernameTextView.setText(it.toString()) }
        binding.skillsTxt.setText(ceo.Skills)
        binding.experienceTxt.setText(ceo.Experience)
    }


    private fun saveUpdatedEmployeeDetails(existingCeo: Ceo?) {
        if (existingCeo == null) {
            Toast.makeText(this, "Employee data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedCeo = existingCeo.copy(
            Skills = binding.skillsTxt.text.toString().ifBlank { existingCeo.Skills },
            Experience = binding.experienceTxt.text.toString().ifBlank { existingCeo.Experience },
        )

        // Update employee using ViewModel
        val rowsAffected = model.updateCEO(updatedCeo)
        if (rowsAffected > 0) {
            Toast.makeText(this, "CEO updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update CEO", Toast.LENGTH_SHORT).show()
        }
    }
}