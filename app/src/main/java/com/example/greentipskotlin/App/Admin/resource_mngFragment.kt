package com.example.greentipskotlin.App.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.Activity.ResourceAdapter
import com.example.greentipskotlin.App.Admin.Activity.ResourceDetailsActivity
import com.example.greentipskotlin.App.Admin.Activity.ResourceInsert
import com.example.greentipskotlin.App.Admin.viewModel.ResourcesViewModel
import com.example.greentipskotlin.databinding.FragmentResourceMngBinding

class resource_mngFragment : Fragment() {

    private var _binding: FragmentResourceMngBinding? = null
    private val binding get() = _binding!!

    private val model: ResourcesViewModel by viewModels()
    private lateinit var resourceAdapter: ResourceAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResourceMngBinding.inflate(inflater,container,false)



        binding.insertResourceImageView.setOnClickListener(){
            startActivity(Intent(requireContext(),ResourceInsert::class.java))
        }

        binding.sortButton.setOnClickListener(){
            toggleSort()
        }

        model.resources.observe(viewLifecycleOwner){ updatedList->
            val listToDisplay = if (isSorted) updatedList.sortedBy { it.description } else updatedList
            resourceAdapter.updateList(listToDisplay)
            binding.resourceCount.text=listToDisplay.size.toString()
        }

        resourceAdapter = ResourceAdapter(emptyList()) { resource ->
            val intent = Intent(requireContext(), ResourceDetailsActivity::class.java).apply {
                putExtra("RESOURCE_ID", resource.resourcesID)
                putExtra("RESOURCE_DESCRIPTION", resource.description)
                putExtra("RESOURCE_DATE", resource.date)
                putExtra("RESOURCE_BILL_NUMBER", resource.billNumber)
                putExtra("RESOURCE_AMOUNT", resource.amount)
                putExtra("RESOURCES_ESTATE_ID_FR", resource.resourcesEstate)
            }
            startActivity(intent)
        }
        binding.resourceRecyclerView.adapter = resourceAdapter

        binding.resourceRecyclerView.layoutManager = LinearLayoutManager(context)



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshData() // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.resources.value?.let { updatedList ->
            resourceAdapter.updateList(if (isSorted) updatedList.sortedBy { it.description } else updatedList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}