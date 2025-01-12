package com.example.greentipskotlin.App.Buyer.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.CreditCard
import com.example.greentipskotlin.databinding.CreditcardCardBinding

class CreditCardAdapter(private var creditCards:List<CreditCard>): RecyclerView.Adapter<CreditCardAdapter.creditCardViewHolder>()  {

    class creditCardViewHolder(private val binding: CreditcardCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(creditCard: CreditCard){
            binding.tvCreditCardID.text=creditCard.cardId.toString()
            binding.tvCreditCardNumber.text=creditCard.cardNumber
            binding.tvCreditCardExpireDate.text=creditCard.expiryDate
            binding.tvCreditCardHolder.text=creditCard.cardHolderName

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): creditCardViewHolder {
        val binding = CreditcardCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return creditCardViewHolder(binding)
    }

    override fun getItemCount(): Int=creditCards.size

    override fun onBindViewHolder(holder: creditCardViewHolder, position: Int) {
        holder.bind(creditCards[position])
    }

    fun updateList(newList: List<CreditCard>) {
        creditCards = newList
        notifyDataSetChanged()
    }


}