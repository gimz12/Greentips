package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.BuyerAdapter
import com.example.greentipskotlin.App.Admin.Activity.BuyerInsert
import com.example.greentipskotlin.App.Admin.Activity.CoconutInsert
import com.example.greentipskotlin.App.Admin.viewModel.BuyerViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerMngBinding


class buyer_mngFragment : Fragment() {

    private var _binding: FragmentBuyerMngBinding? = null
    private val binding get() = _binding!!

    private val model: BuyerViewModel by viewModels()

    private lateinit var buyerAdapter:BuyerAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBuyerMngBinding.inflate(inflater,container,false)

        val addBuyerImg = binding.insertBuyerImageView
        val sortButton = binding.sortButton

        buyerAdapter=BuyerAdapter(model.getAllBuyers())
        binding.buyerRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.buyerRecyclerView.adapter= buyerAdapter

        addBuyerImg.setOnClickListener(){
            onClickAddNewBuyer()
        }
        sortButton.setOnClickListener(){
            toggleSort()
        }

        return binding.root
    }

    private fun toggleSort() {
        if (isSorted) {
            // If already sorted, show unsorted list
            buyerAdapter.updateList(model.getAllBuyers())
        } else {
            // If unsorted, show sorted list
            val sortedList = model.getAllBuyers().sortedBy { it.Buyer_Name }
            buyerAdapter.updateList(sortedList)
        }
        // Toggle the sorting state
        isSorted = !isSorted
    }

    private fun onClickAddNewBuyer() {
        val groupMessageIntent = Intent(requireContext(), BuyerInsert::class.java)
        startActivity(groupMessageIntent)
    }


}