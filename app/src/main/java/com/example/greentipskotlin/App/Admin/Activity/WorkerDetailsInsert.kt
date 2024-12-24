package com.example.greentipskotlin.App.Admin.Activity

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.emp_mngFragment
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Worker
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityWorkerDetailsInsertBinding
import java.util.Calendar

class WorkerDetailsInsert : AppCompatActivity() {

    private lateinit var binding:ActivityWorkerDetailsInsertBinding
    private val model:EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerDetailsInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        if (username != null){
            model.getEmployeeIdByUsername(username).observe(this){employeeId ->

                Log.d("WorkerDetails","EmployeeId : $employeeId")

                val usernameTxT=binding.usernameTextView
                usernameTxT.text = "Worker Username: $username"

                val shiftStartTimeEditText = binding.shiftStartTime
                val shiftEndTimeEditText = binding.shiftEndTime
                val experienceEditText = binding.experienceTxT
                val insertButton = binding.buttonInsert


                val estateNames = mutableListOf("Select a Estate")
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                estateNames.addAll(model.getAllEstateNames())
                binding.estateSpinner.adapter = adapter

                shiftStartTimeEditText.setOnClickListener { showTimePicker(shiftStartTimeEditText) }
                shiftEndTimeEditText.setOnClickListener { showTimePicker(shiftEndTimeEditText) }

                insertButton.setOnClickListener {

                    val estateIdSpinner = binding.estateSpinner.selectedItemId.toInt()
                    val shiftStart = shiftStartTimeEditText.text.toString().trim()
                    val shiftEnd = shiftEndTimeEditText.text.toString().trim()
                    val experience = experienceEditText.text.toString()

                    if (estateIdSpinner != -1 && shiftStart.isNotEmpty() && shiftEnd.isNotEmpty() && employeeId != null) {
                        // Create an Worker object and insert into the database
                        val worker = Worker(employeeId,estateIdSpinner,shiftStart,shiftEnd,experience)
                        model.insertWorker(worker)

                        Toast.makeText(
                            this,
                            "Worker details inserted successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate back to the EmployeeFragment
                        navigateToEmployeeFragment()
                    } else {
                        Toast.makeText(
                            this,
                            "Please enter both start and end times, and ensure employee ID is valid.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


            }
        }
    }

    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            editText.setText(formattedTime)
        }, hour, minute, true)

        timePickerDialog.show()
    }

    // Function to replace the current activity with the EmployeeFragment
    private fun navigateToEmployeeFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with EmployeeFragment
        fragmentTransaction.replace(R.id.fragment_container, emp_mngFragment())  // Use correct container ID
        fragmentTransaction.addToBackStack(null)  // Optional if you want to keep the back stack
        fragmentTransaction.commit()

        finish()  // Close the activity after fragment transaction
    }
}