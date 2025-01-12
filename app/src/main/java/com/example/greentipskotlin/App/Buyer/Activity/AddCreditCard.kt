package com.example.greentipskotlin.App.Buyer.Activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.CreditCardViewModel
import com.example.greentipskotlin.App.Model.CreditCard
import com.example.greentipskotlin.databinding.ActivityAddCreditCardBinding

class AddCreditCard : AppCompatActivity() {

    private lateinit var binding: ActivityAddCreditCardBinding
    private val creditCardViewModel: CreditCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCreditCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve userId from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set the username TextView to display the userId
        binding.usernameTextView.text = "User ID: $userId"

        // Handle Add Credit Card button click
        binding.buttonInsert.setOnClickListener {
            val cardNumber = binding.cardNumber.text.toString().trim()
            val expireDate = binding.expireDate.text.toString().trim()
            val cardHolderName = binding.cardHoldersName.text.toString().trim()

            // Validate input fields
            if (cardNumber.isEmpty() || expireDate.isEmpty() || cardHolderName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create CreditCard object
            val creditCard = CreditCard(
                userId = userId,
                cardNumber = cardNumber,
                expiryDate = expireDate,
                cardHolderName = cardHolderName
            )

            // Insert credit card into database using ViewModel
            creditCardViewModel.addCreditCard(creditCard)
            Toast.makeText(this, "Credit card added successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
