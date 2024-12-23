package com.example.greentipskotlin.App.Admin.Activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.databinding.EmployeeCardBinding

class EmployeeAdapter(
    private var employees: List<Employee>,
    private val onItemClick: (Employee) -> Unit // Lambda function for handling clicks
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = EmployeeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        holder.bind(employees[position])
    }

    override fun getItemCount(): Int = employees.size

    fun updateList(newList: List<Employee>) {
        employees = newList
        notifyItemRangeChanged(0, employees.size) // Optimize data updates
    }

    inner class EmployeeViewHolder(private val binding: EmployeeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Set up click listener on the item view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(employees[position])
                }
            }

            binding.editRoleInfo.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val employee = employees[position]
                    if (employee.employeePositionId == 1) {
                        val context = itemView.context
                        val intent = Intent(context, CeoDetails::class.java).apply {
                            putExtra("EMPLOYEE_ID", employee.employeeId)
                        }
                        context.startActivity(intent)
                    } else if (employee.employeePositionId == 2) {
                        val context = itemView.context
                        val intent = Intent(context, FieldManagerDetails::class.java).apply {
                            putExtra("EMPLOYEE_ID", employee.employeeId)
                        }
                        context.startActivity(intent)
                    }else if (employee.employeePositionId == 4) {
                        val context = itemView.context
                        val intent = Intent(context, AdminDetails::class.java).apply {
                            putExtra("EMPLOYEE_ID", employee.employeeId)
                        }
                        context.startActivity(intent)
                    }
                }
            }


        }

        fun bind(employee: Employee) {
            binding.tvEmployeeName.text = employee.employeeName ?: "N/A"
            binding.tvUsername.text = employee.username ?: "N/A"
            binding.tvPosition.text = employee.employeePositionId?.toString() ?: "N/A"
            binding.tvPhoneNumber.text = employee.phoneNumber ?: "N/A"
            binding.tvGender.text = employee.gender ?: "N/A"
            binding.tvJoinDate.text = employee.joinDate?.toString() ?: "N/A"
            binding.tvAge.text = employee.age?.toString() ?: "N/A"
            binding.tvEmail.text = employee.email ?: "N/A"
        }
    }
}
