package com.example.greentipskotlin.App.Admin.Activity

import android.Manifest
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.BuyerViewModel
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.databinding.ActivityBuyerInsertBinding

class BuyerInsert : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerInsertBinding
    private val model: BuyerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBuyerBtn.setOnClickListener {
            // Get trimmed input values
            val firstName = binding.buyerFirstNameTxt.text.toString().trim()
            val lastName = binding.buyerLastNameTxt.text.toString().trim()
            val buyerName = "$firstName $lastName"
            val buyerPhoneNumber = binding.buyerPhoneNumberTxt.text.toString().trim()
            val buyerCompanyName = binding.buyerCompanyNameTxt.text.toString().trim()
            val buyerCompanyPhoneNumber = binding.buyerCompanyPhoneNumberTxt.text.toString().trim()
            val buyerCompanyAddress = binding.buyerCompanyAddressTxt.text.toString().trim()
            val buyerType = binding.buyerTypeTxt.text.toString().trim()
            val buyerEmail = binding.buyerEmailTxt.text.toString().trim()
            val buyerUsername = binding.buyerUsernameTxt.text.toString().trim()
            val buyerPassword = binding.buyerPasswordTxt.text.toString().trim()

            // Existing validations
            if (firstName.isEmpty()) {
                Toast.makeText(this, "First name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                Toast.makeText(this, "Last name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerPhoneNumber.isEmpty() || buyerPhoneNumber.length < 10) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerCompanyName.isEmpty()) {
                Toast.makeText(this, "Company name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerCompanyPhoneNumber.isEmpty() || buyerCompanyPhoneNumber.length < 10) {
                Toast.makeText(this, "Please enter a valid company phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerCompanyAddress.isEmpty()) {
                Toast.makeText(this, "Company address is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerType.isEmpty()) {
                Toast.makeText(this, "Buyer type is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(buyerEmail).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerUsername.isEmpty()) {
                Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (buyerPassword.isEmpty() || buyerPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- Additional Validations ---
            // Validate that first name and last name contain only letters and spaces.
            val nameRegex = Regex("^[a-zA-Z\\s]+\$")
            if (!nameRegex.matches(firstName)) {
                Toast.makeText(this, "First name must contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!nameRegex.matches(lastName)) {
                Toast.makeText(this, "Last name must contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validate that phone numbers contain only digits.
            val phoneRegex = Regex("^\\d+\$")
            if (!phoneRegex.matches(buyerPhoneNumber)) {
                Toast.makeText(this, "Phone number must contain only digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!phoneRegex.matches(buyerCompanyPhoneNumber)) {
                Toast.makeText(this, "Company phone number must contain only digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validate that the password contains at least one uppercase letter and one digit.
            val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).+\$")
            if (!passwordRegex.matches(buyerPassword)) {
                Toast.makeText(this, "Password must contain at least one uppercase letter and one digit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // --- End Additional Validations ---

            // If all validations pass, insert the buyer
            model.insertBuyer(
                Buyer(
                    null,
                    buyerName,
                    buyerPhoneNumber,
                    buyerCompanyName,
                    buyerCompanyPhoneNumber,
                    buyerCompanyAddress,
                    buyerType,
                    buyerEmail,
                    buyerUsername,
                    buyerPassword
                )
            )
            finish()

            Toast.makeText(this, "Buyer Account Created Successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
