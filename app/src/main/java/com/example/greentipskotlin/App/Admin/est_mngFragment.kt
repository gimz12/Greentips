package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.EstateAdapter
import com.example.greentipskotlin.App.Admin.Activity.EstateInsert
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.databinding.FragmentEstMngBinding

class est_mngFragment : Fragment() {

    private var _binding: FragmentEstMngBinding? = null
    private val binding get() = _binding!!

    // Use the viewModels delegate to obtain your EstateViewModel
    private val model: EstateViewModel by viewModels()

    private lateinit var estateAdapter: EstateAdapter

    private var isSorted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstMngBinding.inflate(inflater, container, false)

        val addEstateImage = binding.insertEstateImageView
        val sortButton = binding.sortButton

        // Initialize adapter with an empty list.
        estateAdapter = EstateAdapter(emptyList())
        binding.employeeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.employeeRecyclerView.adapter = estateAdapter

        // Observe the LiveData from the ViewModel.
        model.estates.observe(viewLifecycleOwner) { estateList ->
            // Determine the list to display based on the sort flag.
            val listToDisplay = if (isSorted) {
                estateList.sortedBy { it.estateName }
            } else {
                estateList
            }

            // Update the adapter with the sorted/unsorted list.
            estateAdapter.updateList(listToDisplay)

            // Set the count text.
            binding.estateCount.text = listToDisplay.size.toString()
        }

        addEstateImage.setOnClickListener {
            onClickAddNewEstate()
        }

        sortButton.setOnClickListener {
            toggleSort()
        }

        return binding.root
    }

    private fun onClickAddNewEstate() {
        val intent = Intent(requireContext(), EstateInsert::class.java)
        startActivity(intent)
    }

    // Method for sorting estates by name.
    private fun toggleSort() {
        isSorted = !isSorted
        model.estates.value?.let { estateList ->
            if (isSorted) {
                estateAdapter.updateList(estateList.sortedBy { it.estateName })
            } else {
                estateAdapter.updateList(estateList)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data whenever the fragment resumes.
        model.refreshData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
