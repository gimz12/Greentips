package com.example.greentipskotlin.App.FinanceManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.greentipskotlin.App.Admin.viewModel.TaskViewModel
import com.example.greentipskotlin.R
import com.example.greentipskotlin.databinding.FragmentFieldManagerDashboardBinding
import com.example.greentipskotlin.databinding.FragmentFinanceManagerDashboardBinding

class FinanceManagerDashboardFragment : Fragment() {

    private var _binding: FragmentFinanceManagerDashboardBinding? = null
    private val binding get() = _binding!!

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentFinanceManagerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

}