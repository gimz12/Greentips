package com.example.greentipskotlin.App.FieldManager.Activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.databinding.TaskCardBinding

class TaskAdapter(
    private var tasks:List<Task>,
    private val onItemClick: (Task) -> Unit): RecyclerView.Adapter<TaskAdapter.taskViewHolder>() {

    class taskViewHolder(private val binding: TaskCardBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(task: Task, onItemClick: (Task) -> Unit) {
            binding.tvTaskID.text=task.TASK_ID.toString()
            binding.tvTaskName.text=task.TASK_NAME
            binding.tvTaskDes.text=task.TASK_DESCRIPTION
            binding.tvTaskType.text=task.TASK_TYPE
            binding.tvTaskAssignDate.text=task.TASK_ASSIGN_DATE
            binding.tvTaskDueDate.text=task.TASK_DUE_DATE

            binding.root.setOnClickListener {
                onItemClick(task)
            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
        val binding = TaskCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return taskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
        holder.bind(tasks[position], onItemClick)
    }

    fun updateList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }

}