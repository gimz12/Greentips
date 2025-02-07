import com.example.greentipskotlin.App.Model.Invoice
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.App.Model.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.*

object EmailHelper {
    private const val SMTP_HOST = "smtp.gmail.com"
    private const val SMTP_PORT = "465"  // SSL port
    private const val SMTP_USER = "greentipsplantation@gmail.com"
    private const val SMTP_PASSWORD = "ivxixkqfsjtdeqsg"  // Use App Password for security!

    suspend fun sendEmail(to: String, subject: String, body: String): Boolean {
        return withContext(Dispatchers.IO) {  // Run in background thread
            try {
                val properties = Properties().apply {
                    put("mail.smtp.host", SMTP_HOST)
                    put("mail.smtp.socketFactory.port", SMTP_PORT)
                    put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.port", SMTP_PORT)
                }

                val session = Session.getInstance(properties, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(SMTP_USER, SMTP_PASSWORD)
                    }
                })

                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(SMTP_USER))
                    addRecipient(Message.RecipientType.TO, InternetAddress(to))
                    this.subject = subject
                    setText(body)
                }

                Transport.send(message)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun sendInvoiceEmail(
        buyerEmail: String,
        orderId: Int,
        orderItems: List<OrderItem>,
        invoice: Invoice
    ): Boolean {
        val subject = "Invoice for Order #$orderId"

        val itemsList = orderItems.joinToString(separator = "\n") { item ->
            "- ${item.ORDER_ITEM_NAME} x ${item.ORDER_ITEM_QUANTITY} @ ${item.ORDER_ITEM_PRICE} = ${item.ORDER_ITEM_TOTAL_PRICE}"
        }

        val body = """
            Dear Customer,
            
            Thank you for your order. Please find your invoice details below:
            
            Order ID: $orderId
            Invoice Payment ID: ${invoice.invoiceId}
            Date: ${invoice.date}
            Time: ${invoice.time}
            
            Items Purchased:
            $itemsList
            
            Subtotal: ${invoice.subtotal}
            Total: ${invoice.total}
            
            We appreciate your business!
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(buyerEmail, subject, body)
    }

    suspend fun sendReceiptEmail(
        buyerEmail: String,
        receipt: Receipt
    ): Boolean {
        val subject = "Receipt for Your Payment"

        val body = """
            Dear Customer,
            
            Thank you for your payment. Please find your receipt details below:
            
            Invoice ID: ${receipt.invoiceId}
            Date: ${receipt.date}
            Time: ${receipt.time}
            
            We appreciate your business!
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(buyerEmail, subject, body)
    }

    suspend fun sendOrderDispatchedEmail(buyerEmail: String, orderId: Int): Boolean {
        val subject = "Your Order #$orderId Has Been Dispatched"
        val body = """
            Dear Customer,
            
            We are pleased to inform you that your order #$orderId has been dispatched and is on its way to you!
            
            You can expect delivery soon.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(buyerEmail, subject, body)
    }

    // 🚚 Order Delivered Email
    suspend fun sendOrderDeliveredEmail(buyerEmail: String, orderId: Int): Boolean {
        val subject = "Your Order #$orderId Has Been Delivered"
        val body = """
            Dear Customer,
            
            We are happy to inform you that your order #$orderId has been successfully delivered.
            
            Thank you for shopping with us. We hope you enjoy your purchase!
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(buyerEmail, subject, body)
    }

    // ❌ Order Canceled Email
    suspend fun sendOrderCanceledEmail(buyerEmail: String, orderId: Int): Boolean {
        val subject = "Your Order #$orderId Has Been Canceled"
        val body = """
            Dear Customer,
            
            We regret to inform you that your order #$orderId has been canceled.
            
            If you have any questions or need further assistance, please contact us.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(buyerEmail, subject, body)
    }

    // 🟢 Supplier Notification: Field Manager Approval
    suspend fun notifyFieldManagerApproved(supplierEmail: String, orderId: Int?): Boolean {
        val subject = "Order #$orderId: Field Manager Approval"
        val body = """
            Dear Supplier,
            
            We are pleased to inform you that your order #$orderId has been approved by the Field Manager.
            
            The order is now awaiting the CEO's decision.
            
            Thank you for your cooperation.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(supplierEmail, subject, body)
    }

    // ✅ Supplier Notification: CEO Approved
    suspend fun notifyCEOApproved(supplierEmail: String, orderId: Int?): Boolean {
        val subject = "Order #$orderId: CEO Approval"
        val body = """
            Dear Supplier,
            
            We are excited to inform you that your offer for order #$orderId has been approved by our CEO.
            
            The payment process will be initiated shortly.
            
            Thank you for your excellent service.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(supplierEmail, subject, body)
    }

    // ❌ Supplier Notification: Offer Declined
    suspend fun notifyOfferDeclined(supplierEmail: String, orderId: Int?): Boolean {
        val subject = "Order #$orderId: Offer Declined"
        val body = """
            Dear Supplier,
            
            We regret to inform you that your offer for order #$orderId has been declined.
            
            Please feel free to reach out if you have any questions.
            
            Thank you for your understanding.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(supplierEmail, subject, body)
    }

    // 💰 Notify Supplier about Payment
    suspend fun notifySupplierPayment(
        supplierEmail: String,
        orderId: Int,
        paymentDate: String,
        paymentTime: String,
        paymentType: String,
        paymentStatus: String,
        paidAmount: Double,
        remainingAmount: Double,
        totalAmount: Double
    ): Boolean {
        val subject = "Payment Confirmation for Order #$orderId"
        val body = """
            Dear Supplier,
            
            We are pleased to inform you that a payment has been made for order #$orderId.
            
            Payment Details:
            - Date: $paymentDate
            - Time: $paymentTime
            - Payment Type: $paymentType
            - Payment Status: $paymentStatus
            - Paid Amount: $$paidAmount
            - Remaining Amount: $$remainingAmount
            - Total Amount: $$totalAmount
            
            Thank you for your service.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(supplierEmail, subject, body)
    }

    // 💰 Notify Supplier about Payment
    suspend fun notifySupplierPartialPayment(
        supplierEmail: String,
        paymentId: Int,
        orderId: Int,
        supplierName: String,
        paymentDate: String,
        paymentTime: String,
        paymentType: String,
        paymentStatus: String,
        paidAmount: Double,
        remainingAmount: Double,
        totalAmount: Double
    ): Boolean {
        val subject = "Partial Payment Update for Order #$orderId"
        val body = """
            Dear $supplierName,
            
            We have processed another partial payment for your order.
            
            Payment Details:
            - Supplier Payment ID: $paymentId
            - Supplier Order ID: $orderId
            - Supplier Name: $supplierName
            - Payment Date: $paymentDate
            - Payment Time: $paymentTime
            - Payment Type: $paymentType
            - Payment Status: $paymentStatus
            - Paid Amount: $$paidAmount
            - Remaining Amount: $$remainingAmount
            - Total Amount: $$totalAmount
            
            Thank you for your continued partnership.
            
            Best regards,
            Green Tips Plantation
        """.trimIndent()

        return sendEmail(supplierEmail, subject, body)
    }
}
