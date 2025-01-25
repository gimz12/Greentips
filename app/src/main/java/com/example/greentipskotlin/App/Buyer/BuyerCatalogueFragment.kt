package com.example.greentipskotlin.App.Buyer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Buyer.Activity.BuyerCatalogueAdapter
import com.example.greentipskotlin.App.Buyer.Activity.ItemDetails
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.databinding.FragmentBuyerCatalogueBinding

class BuyerCatalogueFragment : Fragment() {

    private var _binding: FragmentBuyerCatalogueBinding? = null
    private val binding get() = _binding!!

    private val model: CatalogueViewModel by viewModels()
    private lateinit var buyerCatalogueAdapter: BuyerCatalogueAdapter
    private var isSorted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBuyerCatalogueBinding.inflate(inflater, container, false)

        // Initialize the adapter with an item click listener
        buyerCatalogueAdapter = BuyerCatalogueAdapter(emptyList()) { selectedCatalogue ->
            val intent = Intent(requireContext(), ItemDetails::class.java).apply {
                putExtra("CATALOGUE_NAME", selectedCatalogue.Catalogue_Name)
                putExtra("CATALOGUE_DESCRIPTION", selectedCatalogue.Catalogue_Item_Description)
                putExtra("CATALOGUE_PRICE", selectedCatalogue.Catalogue_Item_Price.toString())
                putExtra("CATALOGUE_QUANTITY", selectedCatalogue.Catalogue_Item_Quantity.toString())
                putExtra("CATALOGUE_IMAGE", selectedCatalogue.Catalogue_Item_Image) // Add image as a URI or resource ID
            }
            startActivity(intent)
        }



        // Set up RecyclerView
        binding.buyerCatalogueRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buyerCatalogueRecyclerView.adapter = buyerCatalogueAdapter

        // Set up sort button listener
        binding.sortButton.setOnClickListener {
            toggleSort()
        }

        // Observe data changes in the ViewModel
        model.catalogItems.observe(viewLifecycleOwner) { updateList ->
            val listToDisplay = if (isSorted) updateList.sortedBy { it.Catalogue_Name } else updateList
            buyerCatalogueAdapter.updateList(listToDisplay)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        model.refreshDataBuyer() // Refresh data whenever the fragment resumes
        val count = buyerCatalogueAdapter.itemCount
        binding.totalItems.text = count.toString()
    }

    private fun toggleSort() {
        isSorted = !isSorted
        model.catalogItems.value?.let { updatedList ->
            buyerCatalogueAdapter.updateList(
                if (isSorted) updatedList.sortedBy { it.Catalogue_Name } else updatedList
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
