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
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.databinding.FragmentCoconutInfoBinding

class coconut_infoFragment : Fragment() {

    private var _binding: FragmentCoconutInfoBinding? = null
    private val binding get() = _binding!!

    private val model: CoconutViewModel by viewModels()
    private lateinit var coconutAdapter: CoconutAdapter
    private var isSorted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoconutInfoBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupListeners()
        observeData()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Ensure fresh data on returning to the fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        coconutAdapter = CoconutAdapter(emptyList())
        binding.coconutRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.coconutRecyclerView.adapter = coconutAdapter
    }

    private fun setupListeners() {
        binding.insertCoconutTreeImageView.setOnClickListener {
            openCoconutInsertActivity()
        }
        binding.sortButton.setOnClickListener {
            toggleSort()
        }
    }

    private fun observeData() {
        model.coconutTrees.observe(viewLifecycleOwner) { coconutList ->
            coconutAdapter.updateList(coconutList)
            binding.totalTrees.text = coconutList.size.toString()
        }
    }

    private fun openCoconutInsertActivity() {
        val intent = Intent(requireContext(), CoconutInsert::class.java)
        startActivity(intent)
    }

    private fun toggleSort() {
        val currentList = model.coconutTrees.value ?: return
        val sortedList = if (isSorted) currentList else currentList.sortedBy { it.coconutType }
        coconutAdapter.updateList(sortedList)
        isSorted = !isSorted
    }
}
