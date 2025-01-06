package com.example.greentipskotlin.App

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.Activity.BuyerInsert
import com.example.greentipskotlin.App.Admin.Activity.CoconutInsert
import com.example.greentipskotlin.App.Admin.Activity.EmployeeInsert
import com.example.greentipskotlin.App.Admin.Activity.EstateInsert
import com.example.greentipskotlin.App.Admin.Activity.IntercropsInsert
import com.example.greentipskotlin.App.Admin.MainActivity
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.FieldManager.CatalogueItemInsert
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityStaffLoginBinding

class Staff_Login : AppCompatActivity() {
    private lateinit var binding: ActivityStaffLoginBinding
    private val model: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStaffLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText = binding.usernameTxt
        val passwordEditText = binding.passwordTxt
        val loginButton = binding.loginBtn


        loginButton.setOnClickListener(){
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call ViewModel to validate the user
            model.validateUser(username, password).observe(this) { positionId ->
                if (positionId != null) {
                    val userDetails = model.getLoggedInUserDetails(username, password)
                    saveLoggedInUser(userDetails)
                    navigateToDashboard(positionId)
                } else {
                    Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToDashboard(positionId: Int) {
        val intent = when (positionId) {
            1 -> Intent(this, EmployeeInsert::class.java)
            2 -> Intent(this, CatalogueItemInsert::class.java)
            3 -> Intent(this, MainActivity::class.java)
            4 -> Intent(this, MainActivity::class.java)
            5 -> Intent(this, IntercropsInsert::class.java)
            else -> null
        }
        if (intent != null) {
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Unknown Role", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveLoggedInUser(employee: Employee?) {
        if (employee != null) {
            val sharedPref = getSharedPreferences("LoggedUser", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putInt("employeeId", employee.employeeId!!)
                putString("name", employee.employeeName)
                putString("email", employee.email)
                putString("address", employee.address)
                putString("phoneNumber", employee.phoneNumber)
                putString("employeePositionId", employee.employeePositionId.toString())
                putString("profileImage", employee.profileImage)
                apply()
            }
        }
    }





}