package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.greentipskotlin.App.Model.Cart
import com.example.greentipskotlin.App.Model.CartDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application): AndroidViewModel(application) {

    private val cartDataProvider = CartDataProvider(application.applicationContext)

    private val _cartItems = MutableLiveData<List<Cart>>()
    val cartItems: LiveData<List<Cart>> get()=_cartItems


    fun fetchCartItems(userId: Int) {
        val cartItemsList = cartDataProvider.getCartItemsByUserId(userId)
        _cartItems.value = cartItemsList  // Set the result to LiveData
    }

    // Function to add an item to the cart
    fun addItemToCart(cart: Cart) {
        cartDataProvider.addItemToCart(cart)
    }


    fun deleteCartItem(cartId: Int?, userId: Int): Boolean {
        return cartDataProvider.deleteCartItem(cartId,userId)
    }

    fun clearCart(userId: Int) {
        cartDataProvider.clearCart(userId)
    }







}