package com.example.greentipskotlin.App.Admin.Activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityUserProfileManagementBinding
import java.text.SimpleDateFormat
import java.util.Locale

class UserProfileManagement : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileManagementBinding

    private val model: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserProfileManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = getLoggedInUser(this)

        if (user != null) {
            // Set the profile image
            val profileImageView: ImageView = binding.supplierCardImageview
            if (user.profileImage != null) {
                // Load the profile image using Glide (or Picasso)
                Glide.with(this)
                    .load(user.profileImage)  // You can also load a URL here
                    .into(profileImageView)
            } else {
                Log.d("ProfileImage", "Profile Image Path: $user.profileImage")

                Toast.makeText(this, "No Profile Picture found", Toast.LENGTH_SHORT).show()
                profileImageView.setImageResource(R.drawable.profilepic)  // Set default image
            }

            // Set the user's name
            val nameTextView: TextView = findViewById(R.id.fullNameTextView)
            nameTextView.text = user.employeeName ?: "Name not available"  // Use default text if name is null

            // Set the user's role (position)
            val roleTextView: TextView = findViewById(R.id.jobRoleTextView)
            roleTextView.text = when (user.employeePositionId) {
                1 -> "CEO"
                2 -> "Field Manager"
                3 -> "Finance Manager"
                4 -> "Admin"
                5 -> "Worker"
                else -> "Role not available"
            }

            val FirstName_txt = binding.FirstNameTxt
            FirstName_txt.text=user.employeeName

            val Email_txt = binding.EmailTxt
            Email_txt.text = user.email

            val Phone_txt = binding.PhoneTxt
            Phone_txt.text = user.phoneNumber

            val Address_txt = binding.AddressTxt
            Address_txt.text = user.address


            // Set the user's address (or email if address is not available)
            val addressTextView: TextView = findViewById(R.id.addressTextView)
            addressTextView.text = user.address ?: "Address not available"  // Use email as a fallback
        } else {
            // Handle case where user is not logged in
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show()
        }


    }


    fun getLoggedInUser(context: Context): Employee? {
        val sharedPref = context.getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)

        // Retrieve values from SharedPreferences
        val name = sharedPref.getString("name", null)
        val phoneNumber = sharedPref.getString("phoneNumber", null)
        val address = sharedPref.getString("address", null)
        val gender = sharedPref.getString("gender", null)
        val joinDateString = sharedPref.getString("joinDate", null)
        val dateOfBirthString = sharedPref.getString("dob", null)
        val age = sharedPref.getInt("age", -1).takeIf { it != -1 }
        val username = sharedPref.getString("username", null)
        val email = sharedPref.getString("email", null)
        val password = sharedPref.getString("password", null)
        val employeePositionIdString = sharedPref.getString("employeePositionId", null)
        val employeePositionId = employeePositionIdString?.toIntOrNull() // Convert it to Integer if needed
        val profileImage = sharedPref.getString("profileImage", null)

        // Define a SimpleDateFormat for parsing date strings
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Parse dates if available
        val joinDate = joinDateString?.let { dateFormat.parse(it) }
        val dateOfBirth = dateOfBirthString?.let { dateFormat.parse(it) }

        // Return Employee with nullable fields
        return Employee(
            employeeId = sharedPref.getInt("employeeId", -1).takeIf { it != -1 },
            employeeName = name,
            phoneNumber = phoneNumber,
            address = address,
            gender = gender,
            joinDate = joinDate,
            dateOfBirth = dateOfBirth,
            age = age,
            username = username,
            email = email,
            password = password,
            employeePositionId = employeePositionId,
            profileImage = profileImage
        )
    }



}