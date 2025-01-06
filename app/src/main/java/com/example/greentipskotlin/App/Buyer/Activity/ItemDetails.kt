package com.example.greentipskotlin.App.Buyer.Activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.CartViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityItemDetailsBinding
import com.example.greentipskotlin.App.Model.Cart
import java.text.SimpleDateFormat
import java.util.*

class ItemDetails : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailsBinding
    private val model: CartViewModel by viewModels()
    private val catalogueViewModel: CatalogueViewModel by viewModels()  // ViewModel for catalogue
    private var totalQuantity: Int = 1
    private var totalPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the Intent
        val name = intent.getStringExtra("CATALOGUE_NAME")
        val description = intent.getStringExtra("CATALOGUE_DESCRIPTION")
        val price = intent.getStringExtra("CATALOGUE_PRICE")
        var quantityAvailable = intent.getStringExtra("CATALOGUE_QUANTITY")
        val imageUri = intent.getStringExtra("CATALOGUE_IMAGE")

        // Populate the UI
        binding.detailedName.text = name ?: "N/A"
        binding.detailedDes.text = description ?: "N/A"
        binding.detailedPrice.text = "Price Per Kg: $price"
        binding.detailedQuantity.text = "Quantity: $quantityAvailable"
        binding.totalPrice.text = price

        if (imageUri != null) {
            binding.detailedImg.setImageURI(Uri.parse(imageUri))
        } else {
            binding.detailedImg.setImageResource(R.drawable.warningbutton_background)
        }

        // Calculate price and quantity
        val itemPrice = price!!.toDouble()
        var quantityAvailableInt = quantityAvailable!!.toInt()

        // Add quantity
        binding.addItem.setOnClickListener {
            if (totalQuantity < quantityAvailableInt) {
                totalQuantity++
                binding.quantity.text = totalQuantity.toString()
                totalPrice = itemPrice * totalQuantity
                binding.totalPrice.text = totalPrice.toString()
            }
        }

        // Remove quantity
        binding.removeItem.setOnClickListener {
            if (totalQuantity > 1) {
                totalQuantity--
                binding.quantity.text = totalQuantity.toString()
                totalPrice = itemPrice * totalQuantity
                binding.totalPrice.text = totalPrice.toString()
            }
        }

        // Add to cart and update catalogue
        binding.addToCart.setOnClickListener {
            addToCart(name, itemPrice, quantityAvailableInt)
        }
    }

    private fun addToCart(itemName: String?, itemPrice: Double, quantityAvailableInt: Int) {
        if (itemName == null) {
            Toast.makeText(this, "Invalid item details!", Toast.LENGTH_SHORT).show()
            return
        }

        // Retrieve User ID from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show()
            return
        }

        // Generate current date
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Create a Cart object
        val cart = Cart(
            CART_USER_ID = userId,
            CART_ITEM_NAME = itemName,
            CART_ITEM_PRICE = itemPrice,
            CART_ITEM_QUANTITY = totalQuantity,
            CART_ITEM_DATE = currentDate,
            CART_ITEM_TOTAL_PRICE = totalPrice
        )

        // Add to Cart database
        model.addItemToCart(cart)
        Toast.makeText(this, "$itemName added to cart successfully!", Toast.LENGTH_SHORT).show()

        // Update catalogue quantity and refresh UI
        catalogueViewModel.updateCatalogueQuantity(itemName, totalQuantity) { isUpdated ->
            if (isUpdated) {
                Toast.makeText(this, "Catalogue updated successfully!", Toast.LENGTH_SHORT).show()

                // Update the displayed quantity in the UI
                val newQuantityAvailable = quantityAvailableInt - totalQuantity
                binding.detailedQuantity.text = "Quantity: $newQuantityAvailable"

                // You can also update the quantityAvailable value to keep it synchronized
                //quantityAvailable = newQuantityAvailable.toString()

            } else {
                Toast.makeText(this, "Failed to update catalogue.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
