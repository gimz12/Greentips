package com.example.greentipskotlin.App.Buyer

import CartAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greentipskotlin.App.Admin.MainActivity
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CartViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CreditCardViewModel
import com.example.greentipskotlin.App.Admin.viewModel.OrderItemViewModel
import com.example.greentipskotlin.App.Buyer.Activity.BuyerOrderPlaced
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.Cart
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.databinding.FragmentBuyerCartBinding
import java.text.SimpleDateFormat
import java.util.*

class BuyerCartFragment : Fragment() {

    private var _binding: FragmentBuyerCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()
    private val creditCardViewModel: CreditCardViewModel by viewModels()
    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()
    private val orderItemViewModel: OrderItemViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerCartBinding.inflate(inflater, container, false)

        // Initialize RecyclerView
        cartAdapter = CartAdapter(mutableListOf()) { cart, _ ->
            handleDeleteItem(cart)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = cartAdapter

        setupPaymentMethodSpinner()

        binding.buyNowBtn.setOnClickListener {
            placeOrder()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            cartViewModel.fetchCartItems(userId)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner, { cartItems ->
            cartAdapter.updateList(cartItems)
            val totalPrice = cartItems.sumOf { it.CART_ITEM_TOTAL_PRICE.toDouble() }
            binding.totalPrice.text = "Total Price: $${"%.2f".format(totalPrice)}"
        })
    }

    private fun setupPaymentMethodSpinner() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            creditCardViewModel.fetchCreditCards(userId)
            creditCardViewModel.creditCards.observe(viewLifecycleOwner) { cards ->
                val spinnerItems = mutableListOf<String>("Cash on Delivery")
                if (cards.isNotEmpty()) {
                    spinnerItems.addAll(cards.map { "Card ending in ${it.cardNumber.takeLast(4)}" })
                } else {
                    spinnerItems.add("Add a Card Here")
                }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerItems
                )
                binding.paymentMethod.adapter = adapter

                binding.paymentMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (spinnerItems[position] == "Add a Card Here") {
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun handleDeleteItem(cart: Cart) {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            val isDeleted = cartViewModel.deleteCartItem(cart.Cart_Id, userId)
            if (isDeleted) {
                cartAdapter.removeItem(cart) { totalPrice ->
                    binding.totalPrice.text = "Total Price: $${"%.2f".format(totalPrice)}"
                }
                Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to delete item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun placeOrder() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)
        val address = sharedPreferences.getString("USER_ADDRESS", "Company Address")
        val totalPriceText = binding.totalPrice.text.toString()
        val totalPrice = totalPriceText.substringAfter("Total Price: $").toDoubleOrNull()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Check if cart is empty
        if (cartAdapter.itemCount == 0) {
            Toast.makeText(requireContext(), "Cart is empty. Please add items to place an order.", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId != -1 && totalPrice != null && address != null) {
            // Create BuyerOrder object
            val buyerOrder = BuyerOrder(
                USER_ID = userId,
                ORDER_COST = totalPrice,
                ORDER_DATE = currentDate,
                ORDER_STATUS = "Processing",
                ORDER_SHIPPING_ADDRESS = address
            )

            // Place the order
            val orderId = buyerOrderViewModel.placeOrders(buyerOrder)
            val orderIdInt = orderId.toInt()

            if (orderId != null) {
                // Insert items into OrderItems table
                cartViewModel.fetchCartItems(userId) // Asynchronously fetch the cart items
                cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
                    for (cartItem in cartItems) {
                        val orderItem = OrderItem(
                            ORDER_ITEM_ORDER_ID = orderIdInt,
                            ORDER_ITEM_NAME = cartItem.CART_ITEM_NAME,
                            ORDER_ITEM_QUANTITY = cartItem.CART_ITEM_QUANTITY,
                            ORDER_ITEM_PRICE = cartItem.CART_ITEM_PRICE,
                            ORDER_ITEM_TOTAL_PRICE = cartItem.CART_ITEM_TOTAL_PRICE
                        )
                        orderItemViewModel.insertOrderItem(orderItem) // Insert each cart item as an order item
                    }

                    // Clear cart items after placing order
                    cartViewModel.clearCart(userId)
                    binding.totalPrice.text = "Total Price: $0.00"

                    Toast.makeText(requireContext(), "Order placed successfully", Toast.LENGTH_SHORT).show()

                    // Pass Order ID and Total Price to BuyerOrderPlaced activity
                    val intent = Intent(requireContext(), BuyerOrderPlaced::class.java).apply {
                        putExtra("ORDER_ID", orderId.toString())
                        putExtra("TOTAL_PRICE", totalPrice)
                    }
                    startActivity(intent)
                }
            } else {
                Toast.makeText(requireContext(), "Failed to place order. Please try again.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Failed to place order. Please check your details.", Toast.LENGTH_SHORT).show()
        }
    }
}
