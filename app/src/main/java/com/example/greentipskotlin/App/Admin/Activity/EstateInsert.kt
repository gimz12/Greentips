package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.EstateViewModel
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityEstateInsertBinding

class EstateInsert : AppCompatActivity() {

    private lateinit var binding: ActivityEstateInsertBinding

    private val model:EstateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEstateInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addEstate = binding.addEstateBtn

        addEstate.setOnClickListener(){
            val estateName=binding.estateNameTxt.text.toString()
            val estateLocation=binding.estateLocationTxt.text.toString()
            val estateSize=binding.estateSizeTxt.text.toString()
            val estateAdditionalInfo=binding.estateAdditionalInfoTxt.text.toString()

            if(estateName.isNotEmpty() && estateLocation.isNotEmpty() && estateSize.isNotEmpty() && estateAdditionalInfo.isNotEmpty()){
                model.insertEstate(Estate(
                    null,estateName,
                    estateLocation,estateSize,estateAdditionalInfo))
                Toast.makeText(this,"Estate Inserted", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Please enter valid Estate Data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}