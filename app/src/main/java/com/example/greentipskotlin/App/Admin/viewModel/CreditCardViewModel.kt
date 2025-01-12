package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.greentipskotlin.App.Model.CreditCard
import com.example.greentipskotlin.App.Model.CreditCardDataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreditCardViewModel(application: Application) : AndroidViewModel(application) {
    private val creditCardDataProvider = CreditCardDataProvider(application)

    private val _creditCards = MutableLiveData<List<CreditCard>>()
    val creditCards: LiveData<List<CreditCard>> get() = _creditCards

    fun fetchCreditCards(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val cards = creditCardDataProvider.getCreditCardsByUserId(userId)
            _creditCards.postValue(cards)
        }
    }

    fun addCreditCard(creditCard: CreditCard) {
        viewModelScope.launch(Dispatchers.IO) {
            creditCardDataProvider.insertCreditCard(creditCard)
            fetchCreditCards(creditCard.userId) // Refresh cards
        }
    }

    fun getCreditCards(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val cards = creditCardDataProvider.getCreditCardsByUserId(userId)
            _creditCards.postValue(cards)
        }
    }


}