package com.example.greentipskotlin.App.Supplier

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
import com.example.greentipskotlin.App.Buyer.Activity.ItemDetails
import com.example.greentipskotlin.App.Supplier.Activity.SupplierCatalogueAdapter
import com.example.greentipskotlin.App.Supplier.Activity.SupplyDetails
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentBuyerCatalogueBinding
import com.example.greentipskotlin.databinding.FragmentSupplierViewCatalogBinding

class supplierViewCatalogFragment : Fragment() {

    private var _binding: FragmentSupplierViewCatalogBinding? = null
    private val binding get() = _binding!!

    private val model: CatalogueViewModel by viewModels()
    private lateinit var supplierCatalogueAdapter: SupplierCatalogueAdapter
    private var isSorted: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSupplierViewCatalogBinding.inflate(inflater, container, false)
        supplierCatalogueAdapter = SupplierCatalogueAdapter(emptyList()) { selectedCatalogue ->
            val intent = Intent(requireContext(), SupplyDetails::class.java).apply {
                putExtra("CATALOGUE_NAME", selectedCatalogue.Catalogue_Name)
                putExtra("CATALOGUE_DESCRIPTION", selectedCatalogue.Catalogue_Item_Description)
                putExtra("CATALOGUE_IMAGE", selectedCatalogue.Catalogue_Item_Image) // Add image as a URI or resource ID
            }
            startActivity(intent)
        }



        // Set up RecyclerView
        binding.buyerCatalogueRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buyerCatalogueRecyclerView.adapter = supplierCatalogueAdapter

        // Set up sort button listener
        binding.sortButton.setOnClickListener {
            toggleSort()
        }

        // Observe data changes in the ViewModel
        model.catalogItems.observe(viewLifecycleOwner) { updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.Catalogue_Name } else updateList
            supplierCatalogueAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshDataSupplier() // Refresh data whenever the fragment resumes
        val count = supplierCatalogueAdapter.itemCount
        binding.totalItems.text = count.toString()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.catalogItems.value?.let { updatedList ->
            supplierCatalogueAdapter.updateList(
                if (isSorted) updatedList.sortedBy { it.Catalogue_Name } else updatedList
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }

}