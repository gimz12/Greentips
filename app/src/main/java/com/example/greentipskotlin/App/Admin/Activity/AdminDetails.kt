package com.example.greentipskotlin.App.Admin.Activity

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.emp_mngFragment
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityAdminDetailsBinding
import java.util.Calendar

class AdminDetails : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDetailsBinding
    private val model: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")  // Get the username

        // Observe the LiveData returned by the ViewModel
        if (username != null) {
            model.getEmployeeIdByUsername(username).observe(this, { employeeId ->
                // Log employeeId to verify it's being fetched correctly
                Log.d("AdminDetails", "Employee ID: $employeeId")

                // Display username on the screen
                val usernameTextView = binding.usernameTextView
                usernameTextView.text = "Admin: $username"

                // Handle shift start and end times with TimePickers
                val shiftStartEditText = binding.shiftStartTime
                val shiftEndEditText = binding.shiftEndTime

                shiftStartEditText.setOnClickListener { showTimePicker(shiftStartEditText) }
                shiftEndEditText.setOnClickListener { showTimePicker(shiftEndEditText) }

                // Insert admin details when the insert button is clicked
                val insertButton = binding.buttonUpdate
                insertButton.setOnClickListener {
                    val shiftStart = shiftStartEditText.text.toString().trim()
                    val shiftEnd = shiftEndEditText.text.toString().trim()

                    if (shiftStart.isNotEmpty() && shiftEnd.isNotEmpty() && employeeId != null) {
                        // Create an Admin object and insert into the database
                        val admin = Admin(employeeId, shiftStart, shiftEnd)
                        model.insertAdminDetails(admin)

                        Toast.makeText(this, "Admin details inserted successfully!", Toast.LENGTH_SHORT).show()

                        // Navigate back to the EmployeeFragment
                        navigateToEmployeeFragment()
                    } else {
                        Toast.makeText(this, "Please enter both start and end times, and ensure employee ID is valid.", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    // Function to show TimePickerDialog
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

