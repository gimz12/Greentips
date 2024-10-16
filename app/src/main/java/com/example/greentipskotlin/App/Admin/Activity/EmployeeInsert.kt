package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.EmployeeViewModel
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityEmployeeInsertBinding
import java.util.Calendar
import java.util.Date

class EmployeeInsert : AppCompatActivity() {

    private lateinit var binding:ActivityEmployeeInsertBinding
    private val model:EmployeeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmployeeInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.addEmployeeBtn.setOnClickListener(){
            val employee_name = binding.firstNameTxt.text.toString() +""+ binding.lastNameTxt.text.toString()
            val employee_phonenumer=binding.phoneNumberTxt.text.toString()
            val employee_address=binding.addressTxt.text.toString()
            val employee_gender=binding.genderSpinner.selectedItem.toString()
            val employee_join_date=Date()

            // Extract date of birth from DatePicker
            val day = binding.birthdayDatePicker.dayOfMonth
            val month = binding.birthdayDatePicker.month
            val year = binding.birthdayDatePicker.year

            //get employee dob DATE format
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            val employee_date_of_birth=calendar.time


            val employee_age=calculateAge(day,month,year)

            val employee_postion_id=binding.positionSpinner.selectedItemId.toInt()
            val employee_username=binding.usernameTxt.text.toString()
            val employee_email=binding.emailTxt.text.toString()
            val employee_password=binding.passwordTxt.text.toString()

            if(employee_name.isNotEmpty() && employee_phonenumer.isNotEmpty() && employee_address.isNotEmpty() && employee_gender.isNotEmpty() && employee_join_date.toString().isNotEmpty() &&
                        employee_date_of_birth.toString().isNotEmpty() && employee_postion_id.toString().isNotEmpty() && employee_username.isNotEmpty() && employee_email.isNotEmpty() &&
                employee_password.isNotEmpty()){

                model.insertEmployee(Employee(
                    employee_name,
                    employee_phonenumer,
                    employee_address,
                    employee_gender,
                    employee_join_date,
                    employee_date_of_birth,
                    employee_age,
                    employee_username,
                    employee_email,
                    employee_password,
                    employee_postion_id
                ))
                Toast.makeText(this,"Employee Inserted", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "Please enter valid Employee Data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun calculateAge(year: Int, month: Int, day: Int): Int{
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