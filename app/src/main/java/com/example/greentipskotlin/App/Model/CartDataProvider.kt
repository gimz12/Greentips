package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CartDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun addItemToCart(cart: Cart){
        greentipsDatabaseHelper.addItemToCart(cart)
    }

    fun getCartItemsByUserId(userId: Int): List<Cart> {
        return greentipsDatabaseHelper.getCartItemsByUserId(userId)
    }

    fun deleteCartItem(cartId: Int?, userId: Int): Boolean {
        return greentipsDatabaseHelper.deleteCartItem(cartId,userId)
    }

    fun clearCart(userId: Int) {
        greentipsDatabaseHelper.clearCartForUser(userId)
    }




}