package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.App.Model.Supplier
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivitySupplierInsertBinding

class SupplierInsert : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierInsertBinding
    private val model: SupplierViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addSupplier = binding.addSupplierBtn

        addSupplier.setOnClickListener {
            // Get trimmed input values
            val firstName = binding.supplierFirstNameTxt.text.toString().trim()
            val lastName = binding.supplierLastNameTxt.text.toString().trim()
            val supplierName = "$firstName $lastName"  // Renamed from buyerName to supplierName
            val supplierPhoneNumber = binding.supplierPhoneNumberTxt.text.toString().trim()
            val supplierCompanyName = binding.supplierCompanyNameTxt.text.toString().trim()
            val supplierCompanyPhoneNumber = binding.supplierCompanyPhoneNumberTxt.text.toString().trim()
            val supplierCompanyAddress = binding.supplierCompanyAddressTxt.text.toString().trim()
            val supplierType = binding.supplierTypeTxt.text.toString().trim()
            val supplierEmail = binding.supplierEmailTxt.text.toString().trim()
            val supplierUserName = binding.supplierUsernameTxt.text.toString().trim()
            val supplierPassword = binding.supplierPasswordTxt.text.toString().trim()

            // --- Additional Validations ---
            // Validate first and last names contain only letters and spaces
            val nameRegex = Regex("^[a-zA-Z\\s]+\$")
            if (!nameRegex.matches(firstName)) {
                Toast.makeText(this, "First name must contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!nameRegex.matches(lastName)) {
                Toast.makeText(this, "Last name must contain only letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validate phone numbers contain only digits
            val phoneRegex = Regex("^\\d+\$")
            if (!phoneRegex.matches(supplierPhoneNumber)) {
                Toast.makeText(this, "Supplier phone number must contain only digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!phoneRegex.matches(supplierCompanyPhoneNumber)) {
                Toast.makeText(this, "Company phone number must contain only digits", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validate that the password contains at least one uppercase letter and one digit.
            val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).+\$")
            if (!passwordRegex.matches(supplierPassword)) {
                Toast.makeText(this, "Password must contain at least one uppercase letter and one digit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // --- End Additional Validations ---

            // Existing validations for non-empty fields
            if (supplierName.isNotEmpty() && supplierPhoneNumber.isNotEmpty() && supplierCompanyName.isNotEmpty() && supplierCompanyPhoneNumber.isNotEmpty()
                && supplierCompanyAddress.isNotEmpty() && supplierType.isNotEmpty() && supplierEmail.isNotEmpty() && supplierUserName.isNotEmpty() && supplierPassword.isNotEmpty()){
                model.insertSupplier(
                    Supplier(
                         null,
                        supplierName,
                        supplierPhoneNumber,
                        supplierCompanyName,
                        supplierCompanyPhoneNumber,
                        supplierCompanyAddress,
                        supplierType,
                        supplierEmail,
                        supplierUserName,
                        supplierPassword
                    )
                )

                Toast.makeText(this, "Supplier Account Created Successfully", Toast.LENGTH_SHORT).show()

                finish()

            } else {
                Toast.makeText(this, "Please enter valid Supplier Data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
