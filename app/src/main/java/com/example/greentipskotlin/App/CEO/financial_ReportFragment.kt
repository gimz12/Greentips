package com.example.greentipskotlin.App.CEO

import android.app.DatePickerDialog
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.BuyerOrderViewModel
import com.example.greentipskotlin.App.Admin.viewModel.SupplierPaymentViewModel
import com.example.greentipskotlin.databinding.FragmentFinancialReportBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class financial_ReportFragment : Fragment() {

    private var _binding: FragmentFinancialReportBinding? = null
    private val binding get() = _binding!!
    private val buyerOrderViewModel: BuyerOrderViewModel by viewModels()
    private val supplierPaymentViewModel: SupplierPaymentViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancialReportBinding.inflate(inflater, container, false)

        setupDatePickers()
        binding.btnGenerateReport.setOnClickListener { generateRevenueReport() }
        binding.btnExportPdf.setOnClickListener { exportToPdf() }

        setupExDatePickers()
        binding.btnExGenerateReport.setOnClickListener { generateExpensesReport() }
        binding.btnExExportPdf.setOnClickListener { exportExToPdf() }

        return binding.root
    }

    private fun setupDatePickers() {
        val dateSetListener = { editText: View, calendar: Calendar ->
            val date = dateFormat.format(calendar.time)
            (editText as? EditText)?.setText(date)
        }

        binding.etStartDate.setOnClickListener {
            showDatePicker { dateSetListener(binding.etStartDate, it) }
        }

        binding.etEndDate.setOnClickListener {
            showDatePicker { dateSetListener(binding.etEndDate, it) }
        }
    }

    private fun setupExDatePickers() {
        val dateSetListener = { editText: View, calendar: Calendar ->
            val date = dateFormat.format(calendar.time)
            (editText as? EditText)?.setText(date)
        }

        binding.etExStartDate.setOnClickListener {
            showDatePicker { dateSetListener(binding.etExStartDate, it) }
        }

        binding.etExEndDate.setOnClickListener {
            showDatePicker { dateSetListener(binding.etExEndDate, it) }
        }
    }

    private fun showDatePicker(onDateSet: (Calendar) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSet(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showExDatePicker(onDateSet: (Calendar) -> Unit) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSet(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun generateRevenueReport() {
        val startDate = binding.etStartDate.text.toString()
        val endDate = binding.etEndDate.text.toString()

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please select both dates", Toast.LENGTH_SHORT).show()
            return
        }

        val revenue = buyerOrderViewModel.getRevenueReport(startDate, endDate)
        binding.tvRevenue.text = "Total Revenue: Rs ${"%.2f".format(revenue)}"
    }

    private fun generateExpensesReport() {
        val startDate = binding.etExStartDate.text.toString()
        val endDate = binding.etExEndDate.text.toString()

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(requireContext(), "Please select both dates", Toast.LENGTH_SHORT).show()
            return
        }

        val expensesList = supplierPaymentViewModel.getExpensesReport(startDate, endDate)
        if (expensesList.isEmpty()) {
            binding.tvExpenses.text = "Total Expenses: Rs 0.00"
            Toast.makeText(requireContext(), "No expenses found in this range", Toast.LENGTH_SHORT).show()
            return
        }

        val subTotal = expensesList.sumOf { it.totalAmount }
        val transactionFee = subTotal * 0.1
        val finalAmount = subTotal + transactionFee

        binding.tvExpenses.text = "Total Expenses: Rs ${"%.2f".format(finalAmount)}"
    }

    private fun exportToPdf() {
        val startDate = binding.etStartDate.text.toString()
        val endDate = binding.etEndDate.text.toString()
        val totalRevenue = buyerOrderViewModel.getRevenueReport(startDate, endDate)
        val orderDetails = buyerOrderViewModel.getOrderDetails(startDate, endDate)

        if (orderDetails.isEmpty()) {
            Toast.makeText(requireContext(), "No revenue data to export", Toast.LENGTH_SHORT).show()
            return
        }

        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "RevenueReport.pdf"
        )

        try {
            val pdfDocument = PdfDocument()
            val pageWidth = 600
            val pageHeight = 900
            val margin = 20
            var pageNumber = 1

            // Helper function to start a new page and draw the header
            fun startNewPage(): PdfDocument.Page {
                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas
                val headerPaint = Paint().apply {
                    textSize = 16f
                    isFakeBoldText = true
                }
                // Draw the header on every page
                canvas.drawText("Revenue Report", 200f, 50f, headerPaint)
                headerPaint.isFakeBoldText = false
                canvas.drawText("Start Date: $startDate", 50f, 80f, headerPaint)
                canvas.drawText("End Date: $endDate", 50f, 110f, headerPaint)
                // Draw column headers
                headerPaint.isFakeBoldText = true
                val columnHeaders = listOf("Order ID", "Order Cost", "Item Name", "Quantity", "Revenue")
                var xPos = 50f
                val headerY = 140f
                val xOffset = 110f
                columnHeaders.forEach { text ->
                    canvas.drawText(text, xPos, headerY, headerPaint)
                    xPos += xOffset
                }
                return page
            }

            // Start the first page and initialize the canvas and yPosition
            var currentPage = startNewPage()
            var canvas = currentPage.canvas
            var yPosition = 160f  // Start drawing content below the headers

            val contentPaint = Paint().apply {
                textSize = 16f
            }

            // Loop through each order and draw its content
            for (order in orderDetails) {
                // Check if there's enough room for order header and at least one item
                if (yPosition + 40f > pageHeight - margin) {
                    // Finish current page and start a new one
                    pdfDocument.finishPage(currentPage)
                    pageNumber++
                    currentPage = startNewPage()
                    canvas = currentPage.canvas
                    yPosition = 160f
                }

                // Draw order-level details (orderId and orderCost)
                canvas.drawText(order.orderId.toString(), 50f, yPosition, contentPaint)
                canvas.drawText("Rs ${order.orderCost}", 160f, yPosition, contentPaint)

                // For each item in the order, check for page overflow before drawing
                for ((idx, item) in order.items.withIndex()) {
                    // If this is not the first item, add extra spacing
                    if (idx > 0) {
                        yPosition += 30f
                        if (yPosition > pageHeight - margin) {
                            pdfDocument.finishPage(currentPage)
                            pageNumber++
                            currentPage = startNewPage()
                            canvas = currentPage.canvas
                            yPosition = 160f
                        }
                    }
                    canvas.drawText(item.name, 270f, yPosition, contentPaint)
                    canvas.drawText(item.quantity.toString(), 380f, yPosition, contentPaint)
                    canvas.drawText("Rs ${item.totalPrice}", 480f, yPosition, contentPaint)
                }
                yPosition += 40f  // Space after finishing one order
            }

            // Draw total revenue at the end
            if (yPosition + 40f > pageHeight - margin) {
                pdfDocument.finishPage(currentPage)
                pageNumber++
                currentPage = startNewPage()
                canvas = currentPage.canvas
                yPosition = 160f
            }
            contentPaint.isFakeBoldText = true
            canvas.drawText("Total Revenue: Rs ${"%.2f".format(totalRevenue)}", 50f, yPosition, contentPaint)

            // Finish the last page
            pdfDocument.finishPage(currentPage)

            // Write the document content to the file
            val outputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()

            Toast.makeText(requireContext(), "PDF saved to ${pdfFile.path}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to generate PDF", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    private fun exportExToPdf() {
        val startDate = binding.etExStartDate.text.toString()
        val endDate = binding.etExEndDate.text.toString()
        val expensesList = supplierPaymentViewModel.getExpensesReport(startDate, endDate)

        if (expensesList.isEmpty()) {
            Toast.makeText(requireContext(), "No expenses data to export", Toast.LENGTH_SHORT).show()
            return
        }

        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "ExpensesReport.pdf"
        )

        try {
            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(600, 1000, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas: Canvas = page.canvas
            val paint = Paint().apply {
                textSize = 16f
                isFakeBoldText = true
            }

            // Title
            canvas.drawText("Expenses Report", 200f, 50f, paint)

            // Date range
            paint.isFakeBoldText = false
            canvas.drawText("Start Date: $startDate", 50f, 80f, paint)
            canvas.drawText("End Date: $endDate", 50f, 110f, paint)

            // Table Headers
            val headers = listOf("Order ID", "User", "Company", "Item", "Qty", "Price", "Total")
            val columnWidths = listOf(60f, 100f, 120f, 80f, 50f, 70f, 80f) // Adjust column widths
            val startX = 40f
            var yPosition = 150f

            paint.isFakeBoldText = true
            headers.forEachIndexed { index, text ->
                canvas.drawText(text, startX + columnWidths.take(index).sum(), yPosition, paint)
            }
            paint.isFakeBoldText = false

            // Table Content
            yPosition += 30f
            var subTotal = 0.0
            expensesList.forEach { expense ->
                val truncatedUserName = truncateText(expense.userName, 12) // Limit User Name to 12 characters
                val truncatedCompanyName = truncateText(expense.companyName, 15) // Limit Company Name to 15 characters

                val values = listOf(
                    expense.orderId.toString(),
                    truncatedUserName,
                    truncatedCompanyName,
                    expense.itemName,
                    expense.itemQuantity.toString(),
                    "Rs ${expense.quantityPrice}",
                    "Rs ${expense.totalAmount}"
                )

                values.forEachIndexed { index, text ->
                    canvas.drawText(text, startX + columnWidths.take(index).sum(), yPosition, paint)
                }

                subTotal += expense.totalAmount
                yPosition += 30f
            }

            // Calculate 10% transaction fee
            val transactionFee = subTotal * 0.1
            val finalAmount = subTotal + transactionFee

            // Display total, transaction fee, and final expenses amount
            paint.isFakeBoldText = true
            yPosition += 20f
            canvas.drawText("Subtotal: Rs ${"%.2f".format(subTotal)}", 50f, yPosition, paint)
            yPosition += 20f
            canvas.drawText("Transaction Fee (10%): Rs ${"%.2f".format(transactionFee)}", 50f, yPosition, paint)
            yPosition += 20f
            canvas.drawText("Final Expenses: Rs ${"%.2f".format(finalAmount)}", 50f, yPosition, paint)

            pdfDocument.finishPage(page)

            val outputStream = FileOutputStream(pdfFile)
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()

            Toast.makeText(requireContext(), "PDF saved to ${pdfFile.path}", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to generate PDF", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    /**
     * Helper function to truncate text and add ellipsis if it's too long.
     */
    private fun truncateText(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }


}
