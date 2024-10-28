package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.BuyerDataProvider

class BuyerViewModel(application: Application):AndroidViewModel(application) {

    private val buyerDataProvider = BuyerDataProvider(application.applicationContext)
    private val buyers=buyerDataProvider.getAllBuyers()

    fun insertBuyer(buyer: Buyer){
        buyerDataProvider.insertBuyer(buyer)
    }

    fun getAllBuyers():List<Buyer>{
        Log.d("BuyerRepository", "Number of Buyers: ${buyers.size}")
        return buyers
    }

}