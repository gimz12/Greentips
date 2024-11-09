package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.viewModel.HarvestInfoViewModel
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityHarvestInfomationInsertBinding
import java.util.Calendar

class HarvestInfomationInsert : AppCompatActivity() {

    private lateinit var binding: ActivityHarvestInfomationInsertBinding
    private val model: HarvestInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHarvestInfomationInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fetch estates and their IDs
        val estates = model.getAllEstates() // Returns List<Estate> where Estate has `name` and `estateId`
        val estateNames = estates.map { it.estateName } // Map the estate names to display in the spinner
        val estatesid = estates // Keep full Estate objects for later use

        // Set up estate spinner adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.estateSpinner.adapter = adapter

        val cropTypes = mutableListOf("Coconut") // Default crop type
        val cropAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cropTypes)
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.cropIdSpinner.adapter = cropAdapter

        // Fetch intercrops when an estate is selected
        // Inside the spinner selection listener
        binding.estateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedEstateId = estatesid[position].estateId
                if (selectedEstateId != null) {
                    // Fetch intercrops for the selected estate using a non-null selectedEstateId
                    val intercrops = model.getIntercropsForEstate(selectedEstateId)


                    // Debug log
                    Log.d(
                        "HarvestInfomationInsert",
                        "Intercrops for estate $selectedEstateId: $intercrops"
                    )

                    cropTypes.clear()
                    cropTypes.add("Coconut")

                    if (intercrops.isNotEmpty()) {
                        cropTypes.addAll(intercrops.map { it.intercropType })
                    }

                    // Log the updated cropTypes
                    Log.d("HarvestInfomationInsert", "Updated cropTypes: $cropTypes")

                    cropAdapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle case when no item is selected
            }
        }



        // Handle the "Add Harvest Info" button click
        binding.addHarvestInfoBtn.setOnClickListener {
            // Get selected crop type ID (convert selectedItemId to Int)
            val cropType = binding.cropIdSpinner.selectedItemId.toInt()

            // Get the selected date
            val day = binding.harvestedDatePicker.dayOfMonth
            val month = binding.harvestedDatePicker.month
            val year = binding.harvestedDatePicker.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)

            val harvestedDate = calendar.time.toString()
            val harvestedQuantity = binding.harvestQuantityTypeTxt.text.toString()
            val additionalInfo = binding.harvestInfoAdditionalInfoTxt.text.toString()
            // Safely unwrap estate ID, ensuring it's not null
            val selectedEstate = estatesid.getOrNull(binding.estateSpinner.selectedItemPosition)
            val harvestedEstate = selectedEstate?.estateId // Safe unwrap of estateId

            val quantity = harvestedQuantity.toIntOrNull() ?: 0 // Convert harvested quantity to int, default to 0 if invalid

            if (cropType != 0 && harvestedDate.isNotEmpty() && harvestedQuantity.isNotEmpty() && harvestedEstate != null) {
                model.insertHarvestInfo(
                    HarvestInfo(
                        null,
                        cropType,
                        harvestedDate,
                        quantity,
                        additionalInfo,
                        harvestedEstate
                    )
                )
                Toast.makeText(this, "Harvest Info Inserted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please Enter Valid Harvest Information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
