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
import com.example.greentipskotlin.App.Admin.emp_mngFragment
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.App.Model.FieldManager
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityFieldManagerDetailsInsertBinding

class FieldManagerDetailsInsert : AppCompatActivity() {

    private lateinit var binding: ActivityFieldManagerDetailsInsertBinding
    private val model: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFieldManagerDetailsInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estateNames = mutableListOf("Select a Estate")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        estateNames.addAll(model.getAllEstateNames())
        binding.estateSpinner.adapter = adapter

        val username = intent.getStringExtra("username")  // Get the username

        if (username != null) {
            model.getEmployeeIdByUsername(username).observe(this) { employeeId ->
                // Log employeeId to verify it's being fetched correctly
                Log.d("FieldManagerDetails", "Employee ID: $employeeId")

                // Display username on the screen
                binding.usernameTextView.text = "FieldManager: $username"

                // Insert FieldManager details when the insert button is clicked
                binding.buttonInsert.setOnClickListener {
                    // Fetch the data from EditText fields when the button is clicked
                    val estateIdSpinner = binding.estateSpinner.selectedItemId.toInt()
                    val qualificationEditText = binding.qualificationTxT.text.toString()
                    val experienceEditText = binding.experienceTxT.text.toString()

                    // Validate the data
                    if (estateIdSpinner != -1 && qualificationEditText.isNotEmpty() && experienceEditText.isNotEmpty() && employeeId != null) {
                        // Create a FieldManager object and insert into the database
                        val fieldManager = FieldManager(employeeId, estateIdSpinner, qualificationEditText, experienceEditText)
                        model.insertFieldManager(fieldManager)

                        Toast.makeText(
                            this,
                            "FieldManager details inserted successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate back to the EmployeeFragment
                        navigateToEmployeeFragment()
                    } else {
                        // Show an error if any validation fails
                        Toast.makeText(
                            this,
                            "Please enter all the data, and ensure employee ID is valid.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

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
