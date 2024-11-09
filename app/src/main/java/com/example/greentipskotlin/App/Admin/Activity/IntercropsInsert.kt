package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.IntercropsViewModel
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityIntercropsInsertBinding
import java.util.Calendar

class IntercropsInsert : AppCompatActivity() {

    private lateinit var binding: ActivityIntercropsInsertBinding
    private val model: IntercropsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntercropsInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estateNames = mutableListOf("Select a Estate")
        //val estateNames = model.getAllEstateNames()
        val addInterCropBtn = binding.addIntercropBtn


        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        estateNames.addAll(model.getAllEstateNames())

        binding.estateSpinner.adapter=adapter

        addInterCropBtn.setOnClickListener(){
            val intercropType = binding.intercropTypeTxt.text.toString()

            val year = binding.plantedDatePicker.year
            val month = binding.plantedDatePicker.month
            val day = binding.plantedDatePicker.dayOfMonth

            val calander = Calendar.getInstance()
            calander.set(year,month,day)

            val intercropPlantedDate = calander.time.toString()

            val intercropAdditionalInfo = binding.intercropAdditionalInfoTxt.text.toString()
            val intercropEstateId=binding.estateSpinner.selectedItemId.toInt()

            if (intercropType.isNotEmpty()&& intercropPlantedDate.isNotEmpty() && intercropAdditionalInfo.isNotEmpty() && intercropEstateId != -1){
                model.insertInterCrops(Intercrops(null,
                    intercropType,
                    intercropPlantedDate,
                    intercropAdditionalInfo,
                    intercropEstateId))
                Toast.makeText(this,"InterCrop Inserted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please Enter Valid InterCrop Data", Toast.LENGTH_SHORT).show()
            }
        }


    }
}