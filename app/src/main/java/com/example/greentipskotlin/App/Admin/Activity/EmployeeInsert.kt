package com.example.greentipskotlin.App.Admin.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.databinding.ActivityEmployeeInsertBinding
import java.util.*

class EmployeeInsert : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeInsertBinding
    private val model: EmployeeViewModel by viewModels()
    private var profileImageUri: Uri? = null

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Button to select image
        binding.selectImageBtn.setOnClickListener {
            openImagePicker()
        }

        // Button to add employee
        binding.addEmployeeBtn.setOnClickListener {
            addEmployee()
        }
    }

    // Method to open image picker
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    // Handling result of the image picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            profileImageUri = data?.data
            if (profileImageUri != null) {
                // Grant persistent access to the URI
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    contentResolver.takePersistableUriPermission(
                        profileImageUri!!,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                binding.profileImageView.setImageURI(profileImageUri) // Display the image
            } else {
                Toast.makeText(this, "Image selection failed. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method to add employee
    private fun addEmployee() {
        val employeeName = binding.firstNameTxt.text.toString() + " " + binding.lastNameTxt.text.toString()
        val phoneNumber = binding.phoneNumberTxt.text.toString()
        val address = binding.addressTxt.text.toString()
        val gender = binding.genderSpinner.selectedItem.toString()
        val joinDate = Date()

        // Extract date of birth from DatePicker
        val day = binding.birthdayDatePicker.dayOfMonth
        val month = binding.birthdayDatePicker.month
        val year = binding.birthdayDatePicker.year

        // Get employee DOB in DATE format
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateOfBirth = calendar.time

        val age = calculateAge(year, month, day)
        val positionId = binding.positionSpinner.selectedItemId.toInt()
        val username = binding.usernameTxt.text.toString()
        val email = binding.emailTxt.text.toString()
        val password = binding.passwordTxt.text.toString()
        val profileImagePath = profileImageUri?.toString()

        // Validate input
        if (employeeName.isNotEmpty() && phoneNumber.isNotEmpty() && address.isNotEmpty() &&
            gender.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && positionId != 0
        ) {
            // Insert new employee data into database
            model.insertEmployee(Employee(
                null,
                employeeName,
                phoneNumber,
                address,
                gender,
                joinDate,
                dateOfBirth,
                age,
                username,
                email,
                password,
                positionId,
                profileImagePath
            ))

            Toast.makeText(this, "Employee Inserted", Toast.LENGTH_SHORT).show()
            finish() // Close the activity
        } else {
            Toast.makeText(this, "Please enter all required details", Toast.LENGTH_SHORT).show()
        }
    }

    // Method to calculate age based on date of birth
    private fun calculateAge(year: Int, month: Int, day: Int): Int {
        val dob = Calendar.getInstance()
        dob.set(year, month, day)
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }
}
