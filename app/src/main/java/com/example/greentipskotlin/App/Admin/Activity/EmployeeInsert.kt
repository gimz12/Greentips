package com.example.greentipskotlin.App.Admin.Activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        private const val REQUEST_PERMISSION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Button to select image
        binding.selectImageBtn.setOnClickListener {
            // Check for storage permission before accessing the gallery
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Use the Storage Access Framework (SAF) to pick an image
                openImagePickerUsingSAF()
            } else {
                // For devices with SDK < 29, use the traditional method
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    openImagePicker()
                } else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION
                    )
                }
            }
        }

        // Button to add employee
        binding.addEmployeeBtn.setOnClickListener {
            val employee_name = binding.firstNameTxt.text.toString() + " " + binding.lastNameTxt.text.toString()
            val employee_phonenumer = binding.phoneNumberTxt.text.toString()
            val employee_address = binding.addressTxt.text.toString()
            val employee_gender = binding.genderSpinner.selectedItem.toString()
            val employee_join_date = Date()

            // Extract date of birth from DatePicker
            val day = binding.birthdayDatePicker.dayOfMonth
            val month = binding.birthdayDatePicker.month
            val year = binding.birthdayDatePicker.year

            // Get employee DOB in DATE format
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val employee_date_of_birth = calendar.time

            val employee_age = calculateAge(day, month, year)

            val employee_postion_id = binding.positionSpinner.selectedItemId.toInt()
            val employee_username = binding.usernameTxt.text.toString()
            val employee_email = binding.emailTxt.text.toString()
            val employee_password = binding.passwordTxt.text.toString()
            val profileImagePath = profileImageUri?.toString()

            if (employee_name.isNotEmpty() && employee_phonenumer.isNotEmpty() && employee_address.isNotEmpty() &&
                employee_gender.isNotEmpty() && employee_join_date.toString().isNotEmpty() &&
                employee_date_of_birth.toString().isNotEmpty() && employee_postion_id.toString().isNotEmpty() &&
                employee_username.isNotEmpty() && employee_email.isNotEmpty() && employee_password.isNotEmpty()
            ) {
                // Insert new employee data into database
                model.insertEmployee(Employee(
                    null,
                    employee_name,
                    employee_phonenumer,
                    employee_address,
                    employee_gender,
                    employee_join_date,
                    employee_date_of_birth,
                    employee_age,
                    employee_username,
                    employee_email,
                    employee_password,
                    employee_postion_id,
                    profileImagePath
                ))
                Toast.makeText(this, "Employee Inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter valid Employee Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method to open image picker using Storage Access Framework (SAF)
    private fun openImagePickerUsingSAF() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"  // Filter for image files
            putExtra(Intent.EXTRA_LOCAL_ONLY, true) // Allow only local file selection
        }
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    // Method to open image picker (Scoped Storage or All Files Access)
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"  // Filter for image files
        }
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    // Handling result of the image picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            profileImageUri = imageUri
            binding.profileImageView.setImageURI(imageUri) // Display the image
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

    // Handling the permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION) {
            // Check if permissions were granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker() // Open image picker if permission is granted
            } else {
                Toast.makeText(this, "Permission denied. Cannot access storage", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
