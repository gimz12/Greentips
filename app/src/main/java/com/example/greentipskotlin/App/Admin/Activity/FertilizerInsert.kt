package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.FertilizerViewModel
import com.example.greentipskotlin.App.Model.Fertilizer
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityFertilizerInsertBinding
import java.util.Calendar

class FertilizerInsert : AppCompatActivity() {

    private lateinit var binding: ActivityFertilizerInsertBinding
    private val model: FertilizerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFertilizerInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estateNames = mutableListOf("Select a Estate")
        val addFertilizer = binding.addFertilizerBtn

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        estateNames.addAll(model.getAllEstateNames())

        binding.estateSpinner.adapter = adapter

        addFertilizer.setOnClickListener(){
            val fertilizerName = binding.fertilizerNameTxt.text.toString()

            val fertilizerType = binding.fertilizerTypeTxt.text.toString()

            val year = binding.receivedDatePicker.year
            val month = binding.receivedDatePicker.month
            val day = binding.receivedDatePicker.dayOfMonth

            val calender = Calendar.getInstance()
            calender.set(year,month,day)

            val receivedDate = calender.time.toString()

            val fertilizerQuantity = binding.fertilizerQuantityTxt.text.toString()

            val fertilizerComposition = binding.fertilizerCompositionTxt.text.toString()

            val fertilizerAdditionalInfo = binding.fertilizerAdditionalInfoTxt.text.toString()

            val fertilizerEstateId=binding.estateSpinner.selectedItemId.toInt()

            if (fertilizerName.isNotEmpty() && fertilizerType.isNotEmpty() && receivedDate.isNotEmpty() && fertilizerQuantity.isNotEmpty()
                && fertilizerComposition.isNotEmpty() && fertilizerEstateId != 0){
                model.insertFertilizer(Fertilizer(null,
                    fertilizerName,
                    fertilizerType,
                    receivedDate,
                    fertilizerQuantity,
                    fertilizerComposition,
                    fertilizerAdditionalInfo,
                    fertilizerEstateId))
                Toast.makeText(this,"Fertilizer Inserted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Enter Valid Fertilizer Data", Toast.LENGTH_SHORT).show()
            }
        }


    }
}