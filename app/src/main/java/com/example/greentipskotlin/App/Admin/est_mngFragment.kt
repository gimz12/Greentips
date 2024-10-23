package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.EmployeeInsert
import com.example.greentipskotlin.App.Admin.Activity.EstateAdapter
import com.example.greentipskotlin.App.Admin.Activity.EstateInsert
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentEstMngBinding


class est_mngFragment : Fragment() {

    private var _binding: FragmentEstMngBinding? = null
    private val binding get() = _binding!!

    private val model: EstateViewModel by viewModels()

    private lateinit var estateAdapter: EstateAdapter

    private var isSorted: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentEstMngBinding.inflate(inflater,container,false)

        val addEstateImage = binding.insertEstateImageView
        val sortButton= binding.sortButton

        estateAdapter = EstateAdapter(model.getAllEstates())
        binding.employeeRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.employeeRecyclerView.adapter=estateAdapter

        addEstateImage.setOnClickListener(){
            onClickAddNewEstate()
        }

        sortButton.setOnClickListener(){
            toggleSort()
        }

        return binding.root
    }

    private fun onClickAddNewEstate() {
        val groupMessageIntent = Intent(requireContext(), EstateInsert::class.java)
        startActivity(groupMessageIntent)  // Start the EmployeeInsert activity
    }

    //Method for SortEmployee using their Name
    private fun toggleSort() {
        if (isSorted) {
            // If already sorted, show unsorted list
            estateAdapter.updateList(model.getAllEstates())
        } else {
            // If unsorted, show sorted list
            val sortedList = model.getAllEstates().sortedBy { it.estateName }
            estateAdapter.updateList(sortedList)
        }
        // Toggle the sorting state
        isSorted = !isSorted
    }

}