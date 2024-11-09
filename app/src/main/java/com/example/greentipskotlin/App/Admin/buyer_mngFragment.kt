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

        buyerAdapter=BuyerAdapter(emptyList())
        binding.buyerRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.buyerRecyclerView.adapter= buyerAdapter

        addBuyerImg.setOnClickListener(){
            onClickAddNewBuyer()
        }
        sortButton.setOnClickListener(){
            toggleSort()
        }

        model.buyers.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.Buyer_Name } else updateList
            buyerAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.buyers.value?.let { updatedList ->
            buyerAdapter.updateList(if (isSorted) updatedList.sortedBy { it.Buyer_Name }else updatedList)
        }
    }

    private fun onClickAddNewBuyer() {
        val groupMessageIntent = Intent(requireContext(), BuyerInsert::class.java)
        startActivity(groupMessageIntent)
    }


}