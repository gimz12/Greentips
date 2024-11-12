package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.ResourcesViewModel
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityFertilizerInsertBinding
import com.example.greentipskotlin.databinding.ActivityResourceInsertBinding
import java.util.Calendar

class ResourceInsert : AppCompatActivity() {

    private lateinit var binding:ActivityResourceInsertBinding
    private val model:ResourcesViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResourceInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estateNames = mutableListOf("Select Estate")
        val addResourceBtn = binding.addResourceBtn

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,estateNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        estateNames.addAll(model.getAllEstateNames())
        binding.estateSpinner.adapter = adapter

        addResourceBtn.setOnClickListener(){
            val resourceDescription = binding.resourceDescriptionTxt.text.toString()

            val year = binding.DatePicker.year
            val month = binding.DatePicker.month
            val day = binding.DatePicker.dayOfMonth

            val calander = Calendar.getInstance()
            calander.set(year,month,day)

            val resourceDate = calander.time.toString()

            val resourceBillNumber = binding.resourceBillNumberTxt.text.toString()
            val resourceAmount = binding.resourceAmountTxt.text.toString()
            val resourcesFinalAmount = resourceAmount.toDouble()
            val resourceEstateId=binding.estateSpinner.selectedItemId.toInt()

            if (resourceDescription.isNotEmpty() && resourceDate.isNotEmpty() && resourceBillNumber.isNotEmpty() &&
                resourceAmount.isNotEmpty() && resourceEstateId != 0){

                model.insertResources(Resources(null,resourceDescription,resourceDate,resourceBillNumber,resourcesFinalAmount,resourceEstateId))

                Toast.makeText(this,"Resource Inserted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Please Enter Valid Resource Data ", Toast.LENGTH_SHORT).show()
            }
        }

    }
}