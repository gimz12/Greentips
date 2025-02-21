package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.emp_mngFragment
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.R

import com.example.greentipskotlin.databinding.ActivityCeoDetailsInsertBinding

class CeoDetailsInsert : AppCompatActivity() {

    private lateinit var binding:ActivityCeoDetailsInsertBinding
    private val model:EmployeeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCeoDetailsInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")  // Get the username

        if (username != null) {
            model.getEmployeeIdByUsername(username).observe(this) { employeeId ->
                // Log employeeId to verify it's being fetched correctly
                Log.d("CeoDetails", "Employee ID: $employeeId")

                // Display username on the screen
                val usernameTextView = binding.usernameTextView
                usernameTextView.text = "CEO: $username"

                // Handle shift start and end times with TimePickers
                val skillsEditText = binding.skillsTxt
                val experienceEditText = binding.experienceTxt


                // Insert admin details when the insert button is clicked
                val insertButton = binding.buttonUpdate
                insertButton.setOnClickListener {
                    val skills = skillsEditText.text.toString()
                    val experience = experienceEditText.text.toString()

                    if (skills.isNotEmpty() && experience.isNotEmpty() && employeeId != null) {
                        // Create an Admin object and insert into the database
                        val ceo = Ceo(employeeId, skills, experience)
                        model.insertCEODetails(ceo)

                        Toast.makeText(
                            this,
                            "CEO details inserted successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate back to the EmployeeFragment
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Please enter both Skills and Experience , and ensure employee ID is valid.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }
    private fun navigateToEmployeeFragment() {
        finish()  // Close the activity after fragment transaction
    }
}