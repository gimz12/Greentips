package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
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
        binding=ActivitySupplierInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addSupplier = binding.addSupplierBtn


        addSupplier.setOnClickListener(){
            val supplierName = binding.supplierFirstNameTxt.text.toString() + " " +binding.supplierLastNameTxt.text.toString()
            val supplierPhoneNumber = binding.supplierPhoneNumberTxt.text.toString()
            val supplierCompanyName = binding.supplierCompanyNameTxt.text.toString()
            val supplierCompanyPhoneNumber = binding.supplierCompanyPhoneNumberTxt.text.toString()
            val supplierCompanyAddress = binding.supplierCompanyAddressTxt.text.toString()
            val supplierType= binding.supplierTypeTxt.text.toString()
            val supplierEmail = binding.supplierEmailTxt.text.toString()
            val supplierUserName = binding.supplierUsernameTxt.text.toString()
            val supplierPassword = binding.supplierPasswordTxt.text.toString()

            if (supplierName.isNotEmpty() && supplierPhoneNumber.isNotEmpty() && supplierCompanyName.isNotEmpty() && supplierCompanyPhoneNumber.isNotEmpty()
                && supplierCompanyAddress.isNotEmpty() && supplierType.isNotEmpty() && supplierEmail.isNotEmpty() && supplierUserName.isNotEmpty() && supplierPassword.isNotEmpty()){
                model.insertSupplier(Supplier(null,
                    supplierName,
                    supplierPhoneNumber,
                    supplierCompanyName,
                    supplierCompanyPhoneNumber,
                    supplierCompanyAddress,
                    supplierType,
                    supplierEmail,
                    supplierUserName,
                    supplierPassword))

                Toast.makeText(this,"Supplier Inserted Success", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please enter valid Supplier Data",Toast.LENGTH_SHORT).show()
            }
        }

    }
}