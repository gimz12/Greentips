package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.App.Model.FieldManager
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityFieldManagerDetailsBinding

class FieldManagerDetails : AppCompatActivity() {

    private lateinit var binding: ActivityFieldManagerDetailsBinding
    private val model: EmployeeViewModel by viewModels()
    private val estateModel:EstateViewModel by viewModels()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFieldManagerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("EMPLOYEE_ID", -1)

        // Check for invalid employee ID
        if (employeeId == -1) {
            Toast.makeText(this, "Invalid Employee ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val existingFieldManager = model.getFieldManagerById(employeeId)
        if (existingFieldManager != null) {
            Log.d("FieldManagerInsert", "Employee ID: ${existingFieldManager.EmployerId}")
            Log.d("FieldManagerInsert", "Estate ID: ${existingFieldManager.Estate_ID}")
            Log.d("FieldManagerInsert", "Qualification: ${existingFieldManager.Qualification}")
            Log.d("FieldManagerInsert", "Experience: ${existingFieldManager.Experience}")
        }


        if (existingFieldManager == null) {
            Toast.makeText(this, "No FieldManager found for this employee", Toast.LENGTH_SHORT).show()
            finish()  // Exit the activity if no data is found
            return
        }
        existingFieldManager?.let {
            populateFieldManagerDetails(it)
        }

        binding.buttonUpdate.setOnClickListener {
            saveUpdatedEmployeeDetails(existingFieldManager)
        }

    }

    fun populateFieldManagerDetails(fieldManager: FieldManager) {
        val estateNames = mutableListOf("Select an Estate")
        val estates = model.getAllEstateNames()

        if (estates.isNullOrEmpty()) {
            Log.e("EstateError", "No estates available")
            Toast.makeText(this, "No estates found", Toast.LENGTH_SHORT).show()
            return
        }

        estateNames.addAll(estates)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.estateSpinner.adapter = adapter

        val estateIdA = fieldManager.Estate_ID
        if (estateIdA != null && estateIdA in 1 until estateNames.size) {
            binding.estateSpinner.setSelection(estateIdA)
        } else {
            Log.e("SpinnerError", "Invalid estate ID: $estateIdA")
        }

        fieldManager.EmployerId?.let { binding.usernameTextView.setText(it.toString()) }
        binding.qualificationTxT.setText(fieldManager.Qualification)
        binding.experienceTxT.setText(fieldManager.Experience)
    }



    private fun saveUpdatedEmployeeDetails(existingFieldManager: FieldManager?) {
        if (existingFieldManager == null) {
            Toast.makeText(this, "Employee data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedFieldManager = existingFieldManager.copy(
            Estate_ID = binding.estateSpinner.selectedItemId.toInt(),
            Qualification = binding.qualificationTxT.text.toString().ifBlank { existingFieldManager.Qualification },
            Experience = binding.experienceTxT.text.toString().ifBlank { existingFieldManager.Experience },
        )

        // Update employee using ViewModel
        val rowsAffected = model.updateFieldManager(updatedFieldManager)
        if (rowsAffected > 0) {
            Toast.makeText(this, "FieldManager updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to update FieldManager", Toast.LENGTH_SHORT).show()
        }
    }
}