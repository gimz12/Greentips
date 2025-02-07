import com.example.greentipskotlin.App.Model.OrderItemReport

data class OrderDetails(
    val orderId: Int,
    val orderDate: String,
    val orderCost: Double,
    val items: MutableList<OrderItemReport> = mutableListOf() // Default value for items
)
