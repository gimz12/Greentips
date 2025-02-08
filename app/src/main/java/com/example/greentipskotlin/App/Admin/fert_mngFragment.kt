package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.FertilizerAdapter
import com.example.greentipskotlin.App.Admin.Activity.FertilizerInsert
import com.example.greentipskotlin.App.Admin.Activity.IntercropsInsert
import com.example.greentipskotlin.App.Admin.viewModel.FertilizerViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentFertMngBinding

class fert_mngFragment : Fragment() {

    private var _binding: FragmentFertMngBinding? = null
    private val binding get() = _binding!!

    private val model: FertilizerViewModel by viewModels()
    private lateinit var fertilizeradapter: FertilizerAdapter

    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFertMngBinding.inflate(inflater,container,false)

        fertilizeradapter = FertilizerAdapter(emptyList())
        binding.fertilizerRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.fertilizerRecyclerView.adapter = fertilizeradapter

        binding.insertFertilizerImageView.setOnClickListener(){
            startActivity(Intent(requireContext(), FertilizerInsert::class.java))
        }

        binding.sortButton.setOnClickListener(){
            toggleSort()
        }

        model.fertilizer.observe(viewLifecycleOwner){updatedList ->
            val  listToDisplay = if (isSorted) updatedList.sortedBy { it.fertilizerName } else updatedList
            fertilizeradapter.updateList(listToDisplay)
            binding.fertilizerCounter.text=listToDisplay.size.toString()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.fertilizer.value?.let { updatedList ->
            fertilizeradapter.updateList(if (isSorted) updatedList.sortedBy { it.fertilizerName } else updatedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}