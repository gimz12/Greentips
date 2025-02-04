package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.App.Model.TaskDataProvider

class TaskViewModel(application: Application):AndroidViewModel(application) {

    private val taskDataProvider=TaskDataProvider(application.applicationContext)

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get()=_tasks

    private val _tasksByEstateId = MutableLiveData<List<Task>>()
    val tasksByEstateId: LiveData<List<Task>> get() = _tasksByEstateId


    fun refreshData(){
        _tasks.value = taskDataProvider.getAllTasks()
    }

    fun insertTask(task: Task){
        taskDataProvider.insertTask(task)
    }

    fun updateTask(task: Task): Int {
        return taskDataProvider.updateTask(task)
    }

    fun deleteTask(taskId: Int): Int {
        return taskDataProvider.deleteTask(taskId)
    }

    fun getTasksByEstateId(estateId: Int?) {
        val orders = taskDataProvider.getTasksByEstateId(estateId)
        _tasksByEstateId.postValue(orders) // Post the data to LiveData
    }


}