import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Cart
import com.example.greentipskotlin.databinding.CartCardBinding

class CartAdapter(
    private var cartItems: MutableList<Cart>,
    private val deleteListener: (Cart, (Double) -> Unit) -> Unit // Updated listener signature
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: CartCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: Cart) {
            binding.productName.text = cart.CART_ITEM_NAME.toString()
            binding.productPrice.text = cart.CART_ITEM_PRICE.toString()
            binding.currentDate.text = cart.CART_ITEM_DATE
            binding.totalQuantity.text = cart.CART_ITEM_QUANTITY.toString()
            binding.totalPrice.text = cart.CART_ITEM_TOTAL_PRICE.toString()

            // Set click listener for the delete button
            binding.delete.setOnClickListener {
                deleteListener(cart) { totalPrice ->
                    // Update the total price after item removal
                    binding.totalPrice.text = "Total Price : $${"%.2f".format(totalPrice)}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    fun updateList(newList: List<Cart>) {
        cartItems = newList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(cart: Cart, updateTotalPrice: (Double) -> Unit) {
        val position = cartItems.indexOf(cart)
        if (position != -1) {
            cartItems.removeAt(position)
            notifyItemRemoved(position)

            // Notify the fragment to update the total price
            updateTotalPrice(getTotalPrice())
        }
    }

    private fun getTotalPrice(): Double {
        return cartItems.sumOf { it.CART_ITEM_TOTAL_PRICE.toDouble() }
    }
}
