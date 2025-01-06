package com.example.greentipskotlin.App.FieldManager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.App.Buyer.Activity.BuyerCatalogueAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.CatalogueItemAdapter
import com.example.greentipskotlin.App.FieldManager.Activity.CatalogueItemUpdate
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerCatalogueBinding
import com.example.greentipskotlin.databinding.FragmentCatalogueItemManageBinding

class CatalogueItemManageFragment : Fragment() {

    private var _binding: FragmentCatalogueItemManageBinding? = null
    private val binding get() = _binding!!

    private val model: CatalogueViewModel by viewModels()

    private lateinit var catalogueItemAdapter: CatalogueItemAdapter

    private var isSorted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCatalogueItemManageBinding.inflate(inflater,container,false)

        binding.insertBuyerImageView.setOnClickListener(){
            val intent = Intent(requireContext(), CatalogueItemInsert::class.java)
            startActivity(intent)
        }
        val sortButton = binding.sortButton

        catalogueItemAdapter = CatalogueItemAdapter(emptyList()) { catalogue ->
            // Navigate to CatalogueItemUpdateActivity with the catalogue ID and type
            val intent = Intent(requireContext(), CatalogueItemUpdate::class.java).apply {
                putExtra("CATALOGUE_ID", catalogue.Catalogue_Id)
                putExtra("CATALOGUE_TYPE", catalogue.Catalogue_Item_Type)
            }
            startActivity(intent)
        }
        binding.catalogueItemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.catalogueItemRecyclerView.adapter = catalogueItemAdapter

        sortButton.setOnClickListener(){
            toggleSort()
        }

        model.catalogItems.observe(viewLifecycleOwner){updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.Catalogue_Name } else updateList
            catalogueItemAdapter.updateList(listToDisplay)
        }




        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshDataFieldManager() // Refresh data whenever the fragment resumes
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.catalogItems.value?.let { updatedList ->
            catalogueItemAdapter.updateList(if (isSorted) updatedList.sortedBy { it.Catalogue_Name }else updatedList)
        }
    }

}