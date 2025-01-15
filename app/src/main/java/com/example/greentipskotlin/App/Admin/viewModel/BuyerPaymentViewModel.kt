package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.BuyerPayment
import com.example.greentipskotlin.App.Model.BuyerPaymentDataProvider

class BuyerPaymentViewModel(application: Application):AndroidViewModel(application) {

    private val buyerPaymentDataProvider = BuyerPaymentDataProvider(application)

    private val _buyerPayments = MutableLiveData<List<BuyerPayment>>()
    val buyerPayment: LiveData<List<BuyerPayment>> get()=_buyerPayments


    fun insertBuyerPayment (buyerPayment: BuyerPayment){
        buyerPaymentDataProvider.insertBuyerPayment(buyerPayment)
    }

    fun refreshData(userId: Int){
        val orderList = buyerPaymentDataProvider.getBuyerPaymentsByUserId(userId)
        _buyerPayments.value = orderList
    }
}