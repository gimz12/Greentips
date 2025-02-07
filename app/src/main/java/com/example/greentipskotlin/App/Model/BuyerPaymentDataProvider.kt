package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class BuyerPaymentDataProvider(context: Context){

    private val greentipsDatabaseHelper= GreentipsDatabaseHelper(context)

    fun insertBuyerPayment(buyerPayment: BuyerPayment):Long{
        return greentipsDatabaseHelper.insertBuyerPayment(buyerPayment)
    }

    fun getBuyerPaymentsByUserId(userId: Int): List<BuyerPayment> {
        return greentipsDatabaseHelper.getBuyerPaymentsByUserId(userId)
    }

}
