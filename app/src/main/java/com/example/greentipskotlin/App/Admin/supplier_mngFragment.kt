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
import com.example.greentipskotlin.App.Admin.Activity.SupplierAdapter
import com.example.greentipskotlin.App.Admin.Activity.SupplierInsert
import com.example.greentipskotlin.App.Admin.viewModel.BuyerViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerMngBinding
import com.example.greentipskotlin.databinding.FragmentSupplierMngBinding


class supplier_mngFragment : Fragment() {

    private var _binding: FragmentSupplierMngBinding? = null
    private val binding get() = _binding!!

    private val model: SupplierViewModel by viewModels()

    private lateinit var supplierAdapter: SupplierAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSupplierMngBinding.inflate(inflater,container,false)

        val addSupplierImg = binding.insertSupplierImageView
        val sortButton = binding.sortButton


        supplierAdapter= SupplierAdapter(emptyList())
        binding.supplierRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.supplierRecyclerView.adapter= supplierAdapter

        addSupplierImg.setOnClickListener(){
            onClickAddNewSupplier()
        }
        sortButton.setOnClickListener(){
            toggleSort()
        }

        model.suppliers.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.Supplier_Name } else updateList
            supplierAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
        binding.supplierCounter.text = supplierAdapter.itemCount.toString()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.suppliers.value?.let { updatedList ->
            supplierAdapter.updateList(if (isSorted) updatedList.sortedBy { it.Supplier_Name }else updatedList)
        }
    }

    private fun onClickAddNewSupplier() {
        val groupMessageIntent = Intent(requireContext(), SupplierInsert::class.java)
        startActivity(groupMessageIntent)
    }

}