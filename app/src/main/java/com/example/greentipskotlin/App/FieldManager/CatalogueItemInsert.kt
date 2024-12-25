package com.example.greentipskotlin.App.FieldManager

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.greentipskotlin.App.Admin.viewModel.CatalogueViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityCatalogueItemInsertBinding

class CatalogueItemInsert : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogueItemInsertBinding
    private val model:CatalogueViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogueItemInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}