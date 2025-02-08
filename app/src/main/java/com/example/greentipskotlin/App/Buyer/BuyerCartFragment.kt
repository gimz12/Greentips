// BuyerCartFragment
package com.example.greentipskotlin.App.Buyer

import CartAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.greentipskotlin.App.Admin.viewModel.BuyerPaymentViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CartViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.App.Admin.viewModel.CreditCardViewModel
import com.example.greentipskotlin.App.Admin.viewModel.InvoiceViewModel
import com.example.greentipskotlin.App.Admin.viewModel.OrderItemViewModel
import com.example.greentipskotlin.App.Admin.viewModel.ReceiptViewModel
import com.example.greentipskotlin.App.Buyer.Activity.AddCreditCard
import com.example.greentipskotlin.App.Buyer.Activity.BuyerOrderPlaced
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.BuyerPayment
import com.example.greentipskotlin.App.Model.Cart
import com.example.greentipskotlin.App.Model.Invoice
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.App.Model.Receipt
import com.example.greentipskotlin.databinding.FragmentBuyerCartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class BuyerCartFragment : Fragment() {

    private var _binding: FragmentBuyerCartBinding? = null
    private val binding get() = _binding!!

    private val cartViewModel: CartViewModel by viewModels()
    private val creditCardViewModel: CreditCardViewModel by viewModels()
    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()
    private val orderItemViewModel: OrderItemViewModel by viewModels()
    private val buyerPaymentViewModel: BuyerPaymentViewModel by viewModels()
    val catalogueViewModel: CatalogueViewModel by viewModels()
    val invoiceViewModel: InvoiceViewModel by viewModels()
    val receiptViewModel: ReceiptViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerCartBinding.inflate(inflater, container, false)

        cartAdapter = CartAdapter(mutableListOf()) { cart, _ ->
            handleDeleteItem(cart)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = cartAdapter

        setupPaymentMethodSpinner()

        binding.proceedToCheckoutBtn.setOnClickListener {
            placeOrder()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)
        val userAddress = sharedPreferences.getString("ADDRESS", "No address available")

        binding.cartShippingDetails.text = "Shipping Address :$userAddress"


        if (userId != -1) {
            cartViewModel.fetchCartItems(userId)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.updateList(cartItems)

            // Calculate subtotal
            val subtotal = cartItems.sumOf { it.CART_ITEM_TOTAL_PRICE.toDouble() }

            // Calculate 10% transaction fee
            val transactionFee = subtotal * 0.10

            // Calculate total (subtotal + transaction fee)
            val totalAmount = subtotal + transactionFee

            // Update UI
            binding.cartSubtotal.text = "Subtotal: Rs ${"%.2f".format(subtotal)}"
            binding.tvTransactionFee.text = "Transaction Fee (10%): Rs ${"%.2f".format(transactionFee)}"
            binding.cartTotal.text = "Total: Rs ${"%.2f".format(totalAmount)}"
        }
    }


    private fun setupPaymentMethodSpinner() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("USER_ID", -1)

        if (userId != -1) {
            creditCardViewModel.fetchCreditCards(userId)
            creditCardViewModel.creditCards.observe(viewLifecycleOwner) { cards ->
                val spinnerItems = mutableListOf("Cash on Delivery")
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
                            startActivity(Intent(requireContext(), AddCreditCard::class.java))
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
                    binding.cartSubtotal.text = "Total Price: $${"%.2f".format(totalPrice)}"
                }

                // Update catalog quantity

                catalogueViewModel.updateCatalogueQuantityRemove(cart.CART_ITEM_NAME, cart.CART_ITEM_QUANTITY) { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Item quantity updated in catalog", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to update catalog quantity", Toast.LENGTH_SHORT).show()
                    }
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
        val address = sharedPreferences.getString("ADDRESS", "Company Address")
        val totalPriceText = binding.cartTotal.text.toString()
        val totalPrice = totalPriceText.substringAfter("Total: Rs ").toDoubleOrNull()
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (cartAdapter.itemCount == 0) {
            Toast.makeText(requireContext(), "Cart is empty. Please add items to place an order.", Toast.LENGTH_SHORT).show()
            return
        }

        if (userId != -1 && totalPrice != null && address != null) {
            // Create a new buyer order.
            val buyerOrder = BuyerOrder(
                USER_ID = userId,
                ORDER_COST = totalPrice,
                ORDER_DATE = currentDate,
                ORDER_STATUS = "Processing",
                ORDER_SHIPPING_ADDRESS = address
            )

            // Place order and get order ID
            val orderId = buyerOrderViewModel.placeOrders(buyerOrder).toInt()
            Log.d("OrderID", "Returned: $orderId")




            // Observe cart items and process order items.
            cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
                // Create a list to collect order items.
                val orderItemsList = mutableListOf<OrderItem>()
                for (cartItem in cartItems) {
                    val orderItem = OrderItem(
                        ORDER_ITEM_ORDER_ID = orderId,
                        ORDER_ITEM_NAME = cartItem.CART_ITEM_NAME,
                        ORDER_ITEM_QUANTITY = cartItem.CART_ITEM_QUANTITY,
                        ORDER_ITEM_PRICE = cartItem.CART_ITEM_PRICE,
                        ORDER_ITEM_TOTAL_PRICE = cartItem.CART_ITEM_TOTAL_PRICE
                    )
                    // Add to the list
                    orderItemsList.add(orderItem)
                    // Insert into database
                    orderItemViewModel.insertOrderItem(orderItem)
                }

                // Process payment details.
                val paymentMethod = binding.paymentMethod.selectedItem.toString()
                val paymentStatus = if (paymentMethod == "Cash on Delivery") "Pending" else "Completed"
                val buyerPayment = BuyerPayment(
                    PAYMENT_ORDER_ID = orderId,
                    PAYMENT_USER_ID = userId,
                    PAYMENT_AMOUNT = totalPrice,
                    PAYMENT_STATUS = paymentStatus,
                    PAYMENT_METHOD = paymentMethod,
                    PAYMENT_DATE_TIME = currentDate
                )
                val paymentId = buyerPaymentViewModel.insertBuyerPayment(buyerPayment).toInt()

                // Create an invoice (for Cash on Delivery, paymentId can be -1 or updated later)
                val invoice = Invoice(
                    orderId = orderId,
                    paymentId = paymentId,
                    date = currentDate,
                    time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
                    subtotal = totalPrice,
                    total = totalPrice
                )
                val invoiceId = invoiceViewModel.insertInvoice(invoice).toInt()

                // Send the invoice email with the collected order items.
                CoroutineScope(Dispatchers.IO).launch {
                    val invoiceSent = EmailHelper.sendInvoiceEmail(
                        "kumalillankoon12@gmail.com",   // Replace with actual email if available.
                        orderId,
                        orderItemsList,
                        invoice
                    )
                    withContext(Dispatchers.Main) {  // Update UI on main thread
                        if (invoiceSent) {
                            println("Invoice email sent successfully.")
                        } else {
                            println("Failed to send invoice email.")
                        }
                    }
                }

                // If payment is completed, create and insert a receipt.
                if (paymentStatus == "Completed") {
                    val receipt = Receipt(
                        invoiceId = invoiceId.toString(),
                        date = currentDate,
                        time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                    )
                    receiptViewModel.insertReceipt(receipt)
                }

                // Clear the cart and update UI.
                cartViewModel.clearCart(userId)
                binding.cartSubtotal.text = "Total Price: $0.00"
                Toast.makeText(requireContext(), "Order placed successfully", Toast.LENGTH_SHORT).show()

                // Navigate to the order placed screen.
                val intent = Intent(requireContext(), BuyerOrderPlaced::class.java).apply {
                    putExtra("ORDER_ID", orderId.toString())
                    putExtra("TOTAL_PRICE", totalPrice)
                }
                startActivity(intent)
            }
        } else {
            Toast.makeText(requireContext(), "Failed to place order. Please check your details.", Toast.LENGTH_SHORT).show()
        }
    }

}
