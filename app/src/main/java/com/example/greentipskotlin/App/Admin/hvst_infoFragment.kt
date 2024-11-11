package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.HarvestAdapter
import com.example.greentipskotlin.App.Admin.Activity.HarvestInfomationInsert
import com.example.greentipskotlin.App.Admin.Activity.IntercropsInsert
import com.example.greentipskotlin.App.Admin.viewModel.HarvestInfoViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentHvstInfoBinding

class hvst_infoFragment : Fragment() {

    private var _binding:FragmentHvstInfoBinding? = null
    private val binding get() = _binding!!

    private val model :HarvestInfoViewModel by viewModels()
    private lateinit var harvetsInfoAdapter: HarvestAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHvstInfoBinding.inflate(inflater,container,false)

        harvetsInfoAdapter = HarvestAdapter(emptyList())
        binding.harvestInfoRecyclerView.layoutManager= LinearLayoutManager(context)
        binding.harvestInfoRecyclerView.adapter = harvetsInfoAdapter

        binding.insertHarvestInfoImageView.setOnClickListener {
            startActivity(Intent(requireContext(), HarvestInfomationInsert::class.java))
        }

        binding.sortButton.setOnClickListener {
            toggleSort()
        }

        model.harvestInfos.observe(viewLifecycleOwner){ updatedList ->
            val listToDisplay = if (isSorted) updatedList.sortedBy { it.harvestType } else updatedList
            harvetsInfoAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
        val count = harvetsInfoAdapter.itemCount
        binding.counter.text = count.toString()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.harvestInfos.value?.let { updatedList ->
            harvetsInfoAdapter.updateList(if (isSorted) updatedList.sortedBy { it.harvestType } else updatedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}