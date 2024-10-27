package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.CoconutAdapter
import com.example.greentipskotlin.App.Admin.Activity.CoconutInsert
import com.example.greentipskotlin.App.Admin.Activity.EstateAdapter
import com.example.greentipskotlin.App.Admin.Activity.EstateInsert
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentCoconutInfoBinding
import com.example.greentipskotlin.databinding.FragmentEstMngBinding


class coconut_infoFragment : Fragment() {

    private var _binding: FragmentCoconutInfoBinding? = null
    private val binding get() = _binding!!

    private val model: CoconutViewModel by viewModels()

    private lateinit var coconutAdapter: CoconutAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentCoconutInfoBinding.inflate(inflater,container,false)

        val addCoconutImg = binding.insertCoconutTreeImageView
        val sortButton = binding.sortButton

        coconutAdapter = CoconutAdapter(model.getAllCoconutTrees())
        binding.coconutRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.coconutRecyclerView.adapter=coconutAdapter

        addCoconutImg.setOnClickListener(){
            onClickAddNewCoconutTree()
        }
        sortButton.setOnClickListener(){
            toggleSort()
        }

        return binding.root
    }

    fun onClickAddNewCoconutTree(){
        val groupMessageIntent = Intent(requireContext(), CoconutInsert::class.java)
        startActivity(groupMessageIntent)
    }

    private fun toggleSort() {
        if (isSorted) {
            // If already sorted, show unsorted list
            coconutAdapter.updateList(model.getAllCoconutTrees())
        } else {
            // If unsorted, show sorted list
            val sortedList = model.getAllCoconutTrees().sortedBy { it.coconutType }
            coconutAdapter.updateList(sortedList)
        }
        // Toggle the sorting state
        isSorted = !isSorted
    }

}