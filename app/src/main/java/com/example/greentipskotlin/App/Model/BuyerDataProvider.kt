package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class BuyerDataProvider (context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertBuyer(buyer: Buyer){
        greentipsDatabaseHelper.insertBuyer(buyer)
    }

    fun getAllBuyers():List<Buyer>{
        return greentipsDatabaseHelper.getAllBuyers()
    }

    fun getBuyerDetailsById(buyerId: Int): Buyer? {
        return greentipsDatabaseHelper.getBuyerDetailsById(buyerId)
    }

}