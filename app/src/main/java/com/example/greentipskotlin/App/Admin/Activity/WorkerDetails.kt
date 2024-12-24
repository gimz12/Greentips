package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Worker
import com.example.greentipskotlin.databinding.ActivityWorkerDetailsBinding

class WorkerDetails : AppCompatActivity() {

    private lateinit var binding:ActivityWorkerDetailsBinding
    private val model:EmployeeViewModel by viewModels()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("EMPLOYEE_ID",-1)

        Log.d("WorkerDebug", "Retrieved WorkerId: $employeeId")

        if (employeeId == -1) {
            Toast.makeText(this, "Invalid Employee ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        val existingWorker = model.getWorkerById(employeeId)
        if (existingWorker != null) {
            Log.d("Worker", "Employee ID: ${existingWorker.EmployerId}")
            Log.d("Worker", "Estate ID: ${existingWorker.Estate_ID}")
            Log.d("Worker", "ShiftStartTime: ${existingWorker.ShiftStartTime}")
            Log.d("Worker", "ShiftEndTime: ${existingWorker.ShiftEndTime}")
            Log.d("Worker", "Experience: ${existingWorker.Experience}")
        }


        if (existingWorker == null) {
            Toast.makeText(this, "No Worker found for this employee", Toast.LENGTH_SHORT).show()
            finish()  // Exit the activity if no data is found
            return
        }
        existingWorker?.let {
            populateWorkerDetails(it)
        }

        binding.buttonUpdate.setOnClickListener {
            saveUpdatedEmployeeDetails(existingWorker)
        }
    }

    fun populateWorkerDetails(worker: Worker) {
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

        val estateIdA = worker.Estate_ID
        if (estateIdA != null && estateIdA in 1 until estateNames.size) {
            binding.estateSpinner.setSelection(estateIdA)
        } else {
            Log.e("SpinnerError", "Invalid estate ID: $estateIdA")
        }

        worker.EmployerId?.let { binding.usernameTextView.setText(it.toString()) }
        binding.shiftStartTime.setText(worker.ShiftStartTime)
        binding.shiftEndTime.setText(worker.ShiftEndTime)
        binding.experienceTxT.setText(worker.Experience)
    }



    private fun saveUpdatedEmployeeDetails(existingWorker: Worker?) {
        if (existingWorker == null) {
            Toast.makeText(this, "Employee data is not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Gather updated values, preserving non-updated fields
        val updatedWorker = existingWorker.copy(
            Estate_ID = binding.estateSpinner.selectedItemId.toInt(),
            ShiftStartTime = binding.shiftStartTime.text.toString().ifBlank { existingWorker.ShiftStartTime },
            ShiftEndTime = binding.shiftEndTime.text.toString().ifBlank { existingWorker.ShiftEndTime },
            Experience = binding.experienceTxT.text.toString().ifBlank { existingWorker.Experience },
        )

        // Update employee using ViewModel
        val rowsAffected = model.updateWorker(updatedWorker)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Worker updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Enter All Details to update Worker", Toast.LENGTH_SHORT).show()
        }
    }
}