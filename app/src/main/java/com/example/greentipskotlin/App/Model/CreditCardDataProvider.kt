package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CreditCardDataProvider (context: Context){

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)


    fun insertCreditCard(creditCard: CreditCard): Long {
        return greentipsDatabaseHelper.insertCreditCard(creditCard)
    }

    fun getCreditCardsByUserId(userId: Int): List<CreditCard> {
        return greentipsDatabaseHelper.getCreditCardsByUserId(userId)
    }

}