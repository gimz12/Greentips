package com.example.greentipskotlin.App.Buyer.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Buyer.BuyerCatalogueFragment
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityBuyerOrderPlacedBinding

class BuyerOrderPlaced : AppCompatActivity() {

    private lateinit var binding: ActivityBuyerOrderPlacedBinding
    private val model:BuyerOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBuyerOrderPlacedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orderId = intent.getStringExtra("ORDER_ID")
        val totalPrice = intent.getDoubleExtra("TOTAL_PRICE", 0.0)

        binding.orderNumber.text=orderId
        binding.totalPrice.text=totalPrice.toString()

        binding.continueShoppingButton.setOnClickListener {
            // Navigate to the activity hosting BuyerCatalogueFragment
            val intent = Intent(this, BuyerMenu::class.java)
            startActivity(intent)
            finish()
        }



    }
}