package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class BuyerOrderDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun placeOrders(buyerOrder: BuyerOrder): Long{
        return greentipsDatabaseHelper.placeOrder(buyerOrder)
    }



}