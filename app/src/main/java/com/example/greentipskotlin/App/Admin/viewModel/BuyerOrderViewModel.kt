package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.BuyerOrderDataProvider

class BuyerOrderViewModel(application: Application):AndroidViewModel(application) {

    private val buyerOrderDataProvider = BuyerOrderDataProvider(application.applicationContext)

    private val _buyerOrders = MutableLiveData<List<BuyerOrder>>()
    val buyerOrders: LiveData<List<BuyerOrder>> get()=_buyerOrders


    fun placeOrders (buyerOrder: BuyerOrder):Long{
        return buyerOrderDataProvider.placeOrders(buyerOrder)
    }

    fun refreshData(userId: Int){
        val orderList = buyerOrderDataProvider.getBuyerOrdersByUserId(userId)
        _buyerOrders.value = orderList
    }



}