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
import com.example.greentipskotlin.databinding.FragmentIntercropsInfoBinding

class intercrops_infoFragment : Fragment() {

    private var _binding: FragmentIntercropsInfoBinding? = null
    private val binding get() = _binding!!

    private val model: IntercropsViewModel by viewModels()
    private lateinit var intercropsAdapter: IntercropAdapter

    private var isSorted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntercropsInfoBinding.inflate(inflater, container, false)

        intercropsAdapter = IntercropAdapter(emptyList())
        binding.interCropsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.interCropsRecyclerView.adapter = intercropsAdapter

        binding.insertIntercropsImageView.setOnClickListener {
            startActivity(Intent(requireContext(), IntercropsInsert::class.java))
        }

        binding.sortButton.setOnClickListener {
            toggleSort()
        }

        model.intercrops.observe(viewLifecycleOwner) { updatedList ->
            val listToDisplay = if (isSorted) updatedList.sortedBy { it.intercropType } else updatedList
            intercropsAdapter.updateList(listToDisplay)
            binding.intercropsCount.text=listToDisplay.size.toString()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.intercrops.value?.let { updatedList ->
            intercropsAdapter.updateList(if (isSorted) updatedList.sortedBy { it.intercropType } else updatedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
