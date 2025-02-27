package com.example.greentipskotlin.App

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.greentipskotlin.App.Admin.Activity.BuyerInsert
import com.example.greentipskotlin.App.Admin.Activity.SupplierInsert
import com.example.greentipskotlin.App.Admin.MainActivity
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Buyer.Activity.BuyerMenu
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.Supplier
import com.example.greentipskotlin.App.Supplier.SupplierMenu
import com.example.greentipskotlin.databinding.ActivityUserLoginBinding
import com.example.greentipskotlin.databinding.CustomAlertDialogBinding

class User_Login : AppCompatActivity() {

    private lateinit var binding: ActivityUserLoginBinding
    private val model: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText = binding.usernameTxt
        val passwordEditText = binding.passwordTxt
        val loginButton = binding.loginBtn


        binding.staffIntent.setOnClickListener(){
            val intent = Intent(this, Staff_Login::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordClickHere.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.newUser.setOnClickListener {
            // Inflate your custom alert dialog layout
            val dialogBinding = CustomAlertDialogBinding.inflate(layoutInflater)

            // Set the title and message of the dialog
            dialogBinding.dialogTitle.text = "Select User Type"
            dialogBinding.dialogMessage.text = "Please choose whether you are a Buyer or a Supplier."

            // Build and show the AlertDialog with two options
            AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setPositiveButton("Buyer") { _, _ ->
                    // Intent to BuyerInsert Activity when Buyer is chosen
                    val intent = Intent(this, BuyerInsert::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Supplier") { _, _ ->
                    // Intent to SupplierInsert Activity when Supplier is chosen
                    val intent = Intent(this, SupplierInsert::class.java)
                    startActivity(intent)
                }
                .show()
        }


        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Authenticate the user using ViewModel
            val result = model.authenticateUser(username, password)
            if (result != null) {
                val (userType, userId) = result

                // Fetch user details based on user type
                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                when (userType) {
                    "Buyer" -> {
                        val buyerDetails: Buyer? = model.getBuyerDetailsById(userId)
                        buyerDetails?.let { buyer ->
                            editor.putString("USER_TYPE", "Buyer")
                            editor.putInt("USER_ID", buyer.Buyer_Id ?: 0)
                            editor.putString("USER_NAME", buyer.Buyer_Name)
                            editor.putString("USER_EMAIL", buyer.Buyer_Email)
                            editor.putString("COMPANY_NAME", buyer.Buyer_Company_Name)
                            editor.putString("PHONE_NUMBER", buyer.Buyer_PhoneNumber)
                            editor.putString("ADDRESS", buyer.Buyer_Company_Address)
                            editor.apply()

                            // Navigate to BuyerMenu
                            startActivity(Intent(this, BuyerMenu::class.java))
                        }
                    }
                    "Supplier" -> {
                        val supplierDetails: Supplier? = model.getSupplierDetailsById(userId)
                        supplierDetails?.let { supplier ->
                            editor.putString("USER_TYPE", "Supplier")
                            editor.putInt("USER_ID", supplier.Supplier_Id ?: 0)
                            editor.putString("USER_NAME", supplier.Supplier_Name)
                            editor.putString("USER_EMAIL", supplier.Supplier_Email)
                            editor.putString("COMPANY_NAME", supplier.Supplier_Company_Name)
                            editor.putString("PHONE_NUMBER", supplier.Supplier_PhoneNumber)
                            editor.apply()

                            // Navigate to SupplierMenu
                            startActivity(Intent(this, SupplierMenu::class.java))
                        }
                    }
                }
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
