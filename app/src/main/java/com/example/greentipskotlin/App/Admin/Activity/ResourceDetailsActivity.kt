package com.example.greentipskotlin.App.Admin.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.greentipskotlin.App.Admin.viewModel.ResourcesViewModel
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.ActivityResourceDetailsBinding

class ResourceDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResourceDetailsBinding
    private lateinit var resourcesViewModel: ResourcesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResourceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resourcesViewModel = ViewModelProvider(this).get(ResourcesViewModel::class.java)

        val resourceId = intent.getIntExtra("RESOURCE_ID", -1)
        val description = intent.getStringExtra("RESOURCE_DESCRIPTION") ?: ""
        val date = intent.getStringExtra("RESOURCE_DATE") ?: ""
        val billNumber = intent.getStringExtra("RESOURCE_BILL_NUMBER") ?: ""
        val amount = intent.getDoubleExtra("RESOURCE_AMOUNT", 0.0)
        val estateId = intent.getIntExtra("RESOURCES_ESTATE_ID_FR", -0)

        // Set the received data to UI fields
        binding.editTextDescription.setText(description)
        binding.editTextDate.setText(date)
        binding.editTextBillNumber.setText(billNumber)
        binding.editTextAmount.setText(amount.toString())
        binding.editEstateId.setText(estateId.toString())

        // Update button
        binding.buttonUpdate.setOnClickListener {
            val updatedResource = Resources(
                resourceId,
                binding.editTextDescription.text.toString(),
                binding.editTextDate.text.toString(),
                binding.editTextBillNumber.text.toString(),
                binding.editTextAmount.text.toString().toDouble(),
                binding.editEstateId.text.toString().toInt()

            )
            resourcesViewModel.updateResource(updatedResource) // Implement this method in ViewModel
            finish()
        }

        // Delete button
        binding.buttonDelete.setOnClickListener {
            resourcesViewModel.deleteResource(resourceId) // Implement this method in ViewModel
            finish()
        }
    }
}