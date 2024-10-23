package com.example.greentipskotlin.App.Admin.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.databinding.EmployeeCardBinding

class EmployeeAdapter (private var employees: List<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =EmployeeCardBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeAdapter.EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount(): Int = employees.size

    //method for getting employees to a new list and notify
    fun updateList(newList: List<Employee>) {
        employees = newList
        notifyDataSetChanged()
    }

    //binding data with List<Employee>
    class EmployeeViewHolder(private val binding: EmployeeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(employee: Employee) {
            binding.tvEmployeeName.text = employee.employeeName
            binding.tvUsername.text = employee.username
            binding.tvPosition.text = employee.employeePositionId.toString()
            binding.tvPhoneNumber.text = employee.phoneNumber
            binding.tvGender.text = employee.gender
            binding.tvJoinDate.text = employee.joinDate.toString()
            binding.tvAge.text = employee.age.toString()
            binding.tvEmail.text = employee.email


        }
    }
}