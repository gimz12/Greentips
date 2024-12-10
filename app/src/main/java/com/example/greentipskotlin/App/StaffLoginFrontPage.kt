package com.example.greentipskotlin.App

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.greentipskotlin.App.Admin.Activity.CoconutInsert
import com.example.greentipskotlin.databinding.ActivityStaffLoginFrontPageBinding

class StaffLoginFrontPage : AppCompatActivity() {
    private lateinit var binding: ActivityStaffLoginFrontPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStaffLoginFrontPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val continueToLogin = binding.continueLogin
        val exit = binding.exit

        continueToLogin.setOnClickListener(){

            val continueToLoginIntent = Intent(this, Staff_Login::class.java)
            startActivity(continueToLoginIntent)

        }
        exit.setOnClickListener(){
            finishAffinity()
        }

    }
}