package com.example.greentipskotlin.App.Buyer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.BuyerAdapter
import com.example.greentipskotlin.App.Admin.Activity.BuyerInsert
import com.example.greentipskotlin.App.Admin.viewModel.CreditCardViewModel
import com.example.greentipskotlin.App.Buyer.Activity.AddCreditCard
import com.example.greentipskotlin.App.Buyer.Activity.CreditCardAdapter
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerCatalogueBinding
import com.example.greentipskotlin.databinding.FragmentPaymentMethodBinding

class PaymentMethodFragment : Fragment() {

    private var _binding: FragmentPaymentMethodBinding? = null
    private val binding get() = _binding!!

    private val model :CreditCardViewModel by viewModels()
    private var isSorted: Boolean = false

    private lateinit var creditCardAdapter: CreditCardAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentMethodBinding.inflate(inflater, container, false)


        creditCardAdapter=CreditCardAdapter(emptyList())
        binding.creditCardRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.creditCardRecyclerView.adapter= creditCardAdapter

        binding.insertCreditCardImageView.setOnClickListener(){
            val creditCardIntent = Intent(requireContext(), AddCreditCard::class.java)
            startActivity(creditCardIntent)
        }

        binding.sortButton.setOnClickListener(){
            toggleSort()
        }

        model.creditCards.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.cardId } else updateList
            creditCardAdapter.updateList(listToDisplay)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            model.getCreditCards(userId) // Refresh data with valid userId
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun toggleSort() {
        isSorted = !isSorted
        val updatedList = if (isSorted) {
            model.creditCards.value?.sortedBy { it.cardId }
        } else {
            model.creditCards.value
        }
        updatedList?.let { creditCardAdapter.updateList(it) }
    }

}