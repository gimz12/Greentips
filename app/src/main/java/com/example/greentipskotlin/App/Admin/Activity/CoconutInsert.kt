package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.CoconutViewModel
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityCoconutInsertBinding
import java.util.Calendar

class CoconutInsert : AppCompatActivity() {

    private lateinit var binding:ActivityCoconutInsertBinding
    private val model:CoconutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCoconutInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estateNames = model.getAllEstateNames()
        val addCoconut = binding.addCoconutBtn

        val adapter =ArrayAdapter(this,android.R.layout.simple_spinner_item,estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.estateSpinner.adapter=adapter

        addCoconut.setOnClickListener(){
            val coconut_Type = binding.coconutTypeTxt.text.toString()

            // Extract date of birth from DatePicker
            val day = binding.plantedDatePicker.dayOfMonth
            val month = binding.plantedDatePicker.month
            val year = binding.plantedDatePicker.year

            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val coconutPlantedDate = calendar.time.toString()

            val coconutAge = calculateCoconutAge(year,month,day).toString()

            val coconutNumbers = binding.numberOfCoconutsTxt.text.toString()
            val coconutHeathStatus = binding.coconutTreeAgeHealthTxt.text.toString()
            val coconutAdditionalInfo = binding.coconutAdditionalInfoTxt.text.toString()
            val coconutEstateId = binding.estateSpinner.selectedItemId.toInt()

            if(coconut_Type.isNotEmpty() && coconutPlantedDate.isNotEmpty() && coconutNumbers.isNotEmpty() && coconutHeathStatus.isNotEmpty() && coconutAdditionalInfo.isNotEmpty()){
                model.insertCoconut(Coconut(null,
                    coconutPlantedDate,
                    coconutNumbers,
                    coconutAge,
                    coconutHeathStatus,
                    coconut_Type,
                    coconutAdditionalInfo,
                    coconutEstateId))
                Toast.makeText(this,"Tree Inserted", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this,"Please Enter Valid Coconut Tree Data", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun calculateCoconutAge(year: Int, month: Int, day: Int): Int{
        val dob =Calendar.getInstance()
        dob.set(year,month,day)

        val today =Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age

    }
}