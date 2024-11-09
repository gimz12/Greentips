package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.BuyerDataProvider

class BuyerViewModel(application: Application):AndroidViewModel(application) {

    private val buyerDataProvider = BuyerDataProvider(application.applicationContext)

    private val _buyers = MutableLiveData<List<Buyer>>()
    val buyers: LiveData<List<Buyer>> get()=_buyers

    init {
        refreshData()
    }

    fun insertBuyer(buyer: Buyer){
        buyerDataProvider.insertBuyer(buyer)
    }

    fun refreshData(){
        _buyers.value = buyerDataProvider.getAllBuyers()
    }


}