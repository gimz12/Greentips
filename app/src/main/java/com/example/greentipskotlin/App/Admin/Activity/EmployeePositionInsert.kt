package com.example.greentipskotlin.App.Admin.Activity

import GreentipsDatabaseHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.EmployeePosition
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityEmployeePositionInsertBinding
import com.example.greentipskotlin.databinding.ActivityMainBinding

class EmployeePositionInsert : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeePositionInsertBinding
    private val model: EmployeeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmployeePositionInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener(){
            val emp_postion_name =binding.etPositionName.text.toString()
            val emp_postion_des =binding.etPositionDescription.text.toString()
            val emp_postion_status =if(binding.rbActive.isChecked)"Active" else "Inactive"

            if (emp_postion_name.isNotEmpty()&&emp_postion_des.isNotEmpty()&&emp_postion_status.isNotEmpty()){
                model.insertEmployeePosition(
                    EmployeePosition(
                        emp_postion_name,
                        emp_postion_des,
                        emp_postion_status
                    )
                )
                Toast.makeText(this,"Employee Position Inserted", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Please enter valid Employee Position", Toast.LENGTH_SHORT).show()
            }

        }

    }
}