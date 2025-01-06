package com.example.greentipskotlin.App.Buyer

import CartAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.viewModel.CartViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.databinding.FragmentBuyerCartBinding

class BuyerCartFragment : Fragment() {

    private var _binding: FragmentBuyerCartBinding? = null
    private val binding get() = _binding!!

    private val model: CartViewModel by viewModels()
    private val catalogueViewModel: CatalogueViewModel by viewModels() // Add Catalogue ViewModel

    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerCartBinding.inflate(inflater, container, false)

        // Set up RecyclerView and Adapter
        cartAdapter = CartAdapter(mutableListOf()) { cart, updateTotalPrice ->
            // Handle delete action here
            val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("USER_ID", -1)

            if (userId != -1) {
                // Call deleteCartItem in the ViewModel
                val isDeleted = model.deleteCartItem(cart.Cart_Id, userId)

                if (isDeleted) {
                    // Deduct the quantity from the catalogue
                    catalogueViewModel.updateCatalogueQuantityRemove(cart.CART_ITEM_NAME.toString(), cart.CART_ITEM_QUANTITY.toInt()) { isUpdated ->
                        if (isUpdated) {
                            // If update is successful, remove the item from the adapter
                            cartAdapter.removeItem(cart) { totalPrice ->
                                // Update the total price after deletion
                                binding.totalPrice.text = "Total Price : $${"%.2f".format(totalPrice)}"
                            }
                            Toast.makeText(requireContext(), "Item deleted and quantity updated in catalogue", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Failed to update catalogue quantity", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to delete item", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = cartAdapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Get user ID from SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        // Fetch cart items for the user ID and update the UI
        if (userId != -1) {
            model.fetchCartItems(userId)
        }

        // Observe changes in cart items
        model.cartItems.observe(viewLifecycleOwner, { cartItems ->
            cartAdapter.updateList(cartItems)

            // Calculate the total price
            val totalPrice = cartItems.sumOf { it.CART_ITEM_TOTAL_PRICE.toDouble() }

            // Update the TextView with the total price
            binding.totalPrice.text = "Total Price : $${"%.2f".format(totalPrice)}"
        })
    }
}
