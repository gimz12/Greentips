package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.BuyerViewModel
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityBuyerInsertBinding

class BuyerInsert : AppCompatActivity() {

    private lateinit var binding:ActivityBuyerInsertBinding
    private val model: BuyerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBuyerInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addCoconut = binding.addBuyerBtn

        addCoconut.setOnClickListener(){
            val buyerName=binding.buyerFirstNameTxt.text.toString()+" "+binding.buyerLastNameTxt.text.toString()
            val buyerPhoneNumber = binding.buyerPhoneNumberTxt.text.toString()
            val buyerCompanyName = binding.buyerCompanyNameTxt.text.toString()
            val buyerCompanyPhoneNumber = binding.buyerCompanyPhoneNumberTxt.text.toString()
            val buyerCompanyAddress = binding.buyerCompanyAddressTxt.text.toString()
            val buyerType = binding.buyerTypeTxt.text.toString()
            val buyerEmail = binding.buyerEmailTxt.text.toString()
            val buyerUsername = binding.buyerUsernameTxt.text.toString()
            val buyerPassword = binding.buyerPasswordTxt.text.toString()

            if (buyerName.isNotEmpty() && buyerPhoneNumber.isNotEmpty() && buyerCompanyName.isNotEmpty()
                && buyerCompanyPhoneNumber.isNotEmpty() && buyerCompanyAddress.isNotEmpty() && buyerType.isNotEmpty() && buyerEmail.isNotEmpty()
                && buyerUsername.isNotEmpty() && buyerPassword.isNotEmpty()){
                model.insertBuyer(Buyer(null,
                    buyerName,
                    buyerPhoneNumber,
                    buyerCompanyName,
                    buyerCompanyPhoneNumber,
                    buyerCompanyAddress,
                    buyerType,
                    buyerEmail,
                    buyerUsername,
                    buyerPassword))

                Toast.makeText(this,"Buyer Inserted Success",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please enter valid Buyer Data",Toast.LENGTH_SHORT).show()
            }
        }


    }
}