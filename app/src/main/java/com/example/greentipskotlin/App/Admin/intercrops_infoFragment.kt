package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.IntercropAdapter
import com.example.greentipskotlin.App.Admin.Activity.IntercropsInsert
import com.example.greentipskotlin.App.Admin.viewModel.IntercropsViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentCoconutInfoBinding
import com.example.greentipskotlin.databinding.FragmentIntercropsInfoBinding

class intercrops_infoFragment : Fragment() {


    private var _binding:FragmentIntercropsInfoBinding? = null
    private val binding get() = _binding!!

    private val model:IntercropsViewModel by viewModels()

    private lateinit var intercropsAdapter: IntercropAdapter

    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIntercropsInfoBinding.inflate(inflater,container,false)

        val intercropAddImg = binding.insertIntercropsImageView
        val sortButton = binding.sortButton

        intercropsAdapter = IntercropAdapter(model.getAllIntercrop())
        binding.interCropsRecyclerView.layoutManager=LinearLayoutManager(context)
        binding.interCropsRecyclerView.adapter=intercropsAdapter

        intercropAddImg.setOnClickListener(){
            onClickAddNewInterCrop()
        }

        sortButton.setOnClickListener(){
            toggleSort()
        }

        return binding.root
    }

    private fun toggleSort() {
        if (isSorted) {
            // If already sorted, show unsorted list
            intercropsAdapter.updateList(model.getAllIntercrop())
        } else {
            // If unsorted, show sorted list
            val sortedList = model.getAllIntercrop().sortedBy { it.intercropType }
            intercropsAdapter.updateList(sortedList)
        }
        // Toggle the sorting state
        isSorted = !isSorted
    }

    private fun onClickAddNewInterCrop() {
        val intercropsIntent = Intent(requireContext(),IntercropsInsert::class.java)
        startActivity(intercropsIntent)
    }



}