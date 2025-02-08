package com.example.greentipskotlin.App.CEO

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.databinding.FragmentCustomReportsBinding
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import com.example.greentipskotlin.App.Model.CoconutProductionReport
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.IntercropsViewModel
import com.example.greentipskotlin.App.Admin.viewModel.ResourcesViewModel
import com.example.greentipskotlin.App.Model.IntercropProductionReport
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class custom_ReportsFragment : Fragment() {

    private var _binding: FragmentCustomReportsBinding? = null
    private val binding get() = _binding!!
    private val coconutViewModel: CoconutViewModel by viewModels()
    private val intercropsViewModel: IntercropsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCustomReportsBinding.inflate(inflater, container, false)

        binding.btnGenerateReport.setOnClickListener {
            val reportData = coconutViewModel.getCoconutProductionReport() // Get data from ViewModel
            generateCoconutProductionReportPdf(requireContext(), reportData)
        }

        binding.btnIntercropGenerateReport.setOnClickListener {
            val reportDataIntercrops = intercropsViewModel.getEstateWiseIntercropReport()
            if (reportDataIntercrops.isEmpty()) {
                Toast.makeText(requireContext(), "No data available for the report.", Toast.LENGTH_SHORT).show()
            } else {
                generateEstateWiseIntercropReportPdf(requireContext(), reportDataIntercrops)
            }
        }



        return binding.root
    }

    fun generateCoconutProductionReportPdf(
        context: Context,
        reportData: List<CoconutProductionReport>
    ) {
        // Define page dimensions
        val pageWidth = 600
        val pageHeight = 800

        // Create a new PdfDocument
        val pdfDocument = PdfDocument()

        // Track the current page number manually
        var currentPageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas: Canvas = page.canvas

        // Prepare paint for drawing text
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }

        // Adjusted column positions to avoid overlapping (removed Plant Date)
        val xPositions = listOf(10f, 70f, 150f, 210f, 270f, 450f)  // Adjusted for fewer columns
        // Define header titles matching the data fields (removed Plant Date)
        val headers = listOf("Estate ID", "No. of Coconuts", "Tree Age", "Tree Health", "Tree Type", "Additional Info")

        // Draw header row on the first page
        val headerY = 40f
        headers.forEachIndexed { index, header ->
            canvas.drawText(header, xPositions[index], headerY, paint)
        }
        // Draw a line below header
        canvas.drawLine(10f, headerY + 5f, pageWidth - 10f, headerY + 5f, paint)

        // Starting y-position for data rows
        var yPosition = headerY + 20f

        // Draw each row from reportData
        for (report in reportData) {
            canvas.drawText(report.estateId.toString(), xPositions[0], yPosition, paint)
            canvas.drawText(report.numberOfCoconuts.toString(), xPositions[1], yPosition, paint)
            canvas.drawText(report.treeAge, xPositions[2], yPosition, paint)
            canvas.drawText(report.treeHealth, xPositions[3], yPosition, paint)
            canvas.drawText(report.treeType, xPositions[4], yPosition, paint)
            canvas.drawText(report.additionalInfo, xPositions[5], yPosition, paint)
            yPosition += 20f

            // If near the bottom of the page, finish current page and start a new one
            if (yPosition > pageHeight - 40) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas

                // Optionally, draw a "Continued..." note at the top
                canvas.drawText("Continued...", 10f, 20f, paint)

                // Re-draw header on the new page
                headers.forEachIndexed { index, header ->
                    canvas.drawText(header, xPositions[index], 40f, paint)
                }
                yPosition = 60f // Reset yPosition below header
            }
        }

        // --- Draw Summary Section ---
        // Add some vertical space before the summary
        yPosition += 20f

        // Group the report data by estate and calculate total coconuts per estate
        val estateTotals: Map<Int, Int> = reportData.groupBy { it.estateId }
            .mapValues { entry -> entry.value.sumOf { it.numberOfCoconuts } }

        // Calculate grand total over all estates
        val grandTotal = estateTotals.values.sum()

        // Check for page space; if insufficient, start a new page
        if (yPosition > pageHeight - 60) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            yPosition = 40f
        }

        // Draw summary header
        paint.textSize = 14f
        canvas.drawText("Summary", 10f, yPosition, paint)
        yPosition += 20f
        paint.textSize = 12f

        // List totals per estate
        estateTotals.forEach { (estateId, total) ->
            // Check for page space
            if (yPosition > pageHeight - 40) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPosition = 40f
            }
            canvas.drawText("Estate $estateId: $total coconuts", 10f, yPosition, paint)
            yPosition += 20f
        }
        // Draw grand total
        if (yPosition > pageHeight - 40) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
            page = pdfDocument.startPage(pageInfo)
            canvas = page.canvas
            yPosition = 40f
        }
        canvas.drawText("Grand Total: $grandTotal coconuts", 10f, yPosition, paint)

        // Finish the last page
        pdfDocument.finishPage(page)

        // Save the PDF file to the Downloads/Reports folder
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Reports")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "Coconut_Production_Report.pdf")
        try {
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            outputStream.close()
            pdfDocument.close()

            // Optionally, force a media scan so the file is visible
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))

            Toast.makeText(context, "PDF Report saved to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving PDF report", Toast.LENGTH_LONG).show()
        }
    }

    fun generateEstateWiseIntercropReportPdf(
        context: Context,
        reportData: List<IntercropProductionReport>
    ) {
        // PDF document and page size
        val pageWidth = 600
        val pageHeight = 800
        val pdfDocument = PdfDocument()

        // Start the first page
        var currentPageNumber = 1
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas: Canvas = page.canvas

        // Common paint for text
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
        }

        // yPosition to track vertical placement on the page.
        var yPosition = 40f

        // Helper function to check for page break and start a new page if needed.
        fun checkPageBreak(minYRequired: Float) {
            if (yPosition > pageHeight - minYRequired) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, currentPageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPosition = 40f
            }
        }

        // ==============================
        // Section 1: Detailed Report
        // ==============================

        // Title for detailed section
        canvas.drawText("Detailed Report", 10f, yPosition, paint)
        yPosition += 20f

        // Adjusted column headers and positions for detailed records:
        // (Using 4 columns: Estate ID, Intercrop Type, Planted Date, Additional Info)
        // The new positions provide more room for the Planted Date and Additional Info columns.
        val xPositionsDetailed = listOf(10f, 70f, 200f, 450f)
        val headersDetailed = listOf("Estate ID", "Intercrop Type", "Planted Date", "Additional Info")
        headersDetailed.forEachIndexed { index, header ->
            canvas.drawText(header, xPositionsDetailed[index], yPosition, paint)
        }
        yPosition += 15f
        canvas.drawLine(10f, yPosition, pageWidth - 10f, yPosition, paint)
        yPosition += 15f

        // Print each detailed record
        for (record in reportData) {
            // Check if we need a new page (reserve about 40 pixels at the bottom)
            checkPageBreak(40f)

            // Draw each column at the updated positions
            canvas.drawText(record.estateId.toString(), xPositionsDetailed[0], yPosition, paint)
            canvas.drawText(record.intercropType, xPositionsDetailed[1], yPosition, paint)
            canvas.drawText(record.plantedDate, xPositionsDetailed[2], yPosition, paint)
            canvas.drawText(record.additionalInfo, xPositionsDetailed[3], yPosition, paint)
            yPosition += 20f
        }

        // Add some gap after the detailed section
        yPosition += 20f
        checkPageBreak(40f)

        // ==============================
        // Section 2: Summary Report
        // ==============================
        canvas.drawText("Summary Report", 10f, yPosition, paint)
        yPosition += 20f

        // Compute the summary data:
        // For each estate (grouped by estateId) group the records by intercropType and count them.
        val summaryData: Map<Int, Map<String, Int>> = reportData.groupBy { it.estateId }
            .mapValues { entry ->
                entry.value.groupBy { it.intercropType }.mapValues { it.value.size }
            }

        // Iterate over each estate summary, sorted by estate id
        val sortedEstateIds = summaryData.keys.sorted()
        for (estateId in sortedEstateIds) {
            checkPageBreak(40f)
            canvas.drawText("Estate ID: $estateId", 10f, yPosition, paint)
            yPosition += 20f

            val plantSummary = summaryData[estateId] ?: continue
            // For each plant type (intercrop type) in this estate, print the count.
            for ((plantType, count) in plantSummary) {
                checkPageBreak(40f)
                // Indent plant details a bit.
                canvas.drawText("   $plantType : $count", 20f, yPosition, paint)
                yPosition += 20f
            }
            // Add extra spacing after each estate's summary
            yPosition += 10f
        }

        // Finish the last page and close the document
        pdfDocument.finishPage(page)

        // Save the PDF file to the Downloads/Reports folder
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Reports")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "Estate_Wise_Intercrop_Report.pdf")
        try {
            val outputStream = FileOutputStream(file)
            pdfDocument.writeTo(outputStream)
            outputStream.close()
            pdfDocument.close()

            // Trigger media scan to make the file visible
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
            Toast.makeText(context, "PDF Report saved to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error saving PDF report", Toast.LENGTH_LONG).show()
        }
    }







}
