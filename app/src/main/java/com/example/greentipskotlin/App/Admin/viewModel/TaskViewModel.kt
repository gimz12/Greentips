package com.example.greentipskotlin.App.Admin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.App.Model.TaskDataProvider
import com.example.greentipskotlin.App.Model.Worker

class TaskViewModel(application: Application):AndroidViewModel(application) {

    private val taskDataProvider=TaskDataProvider(application.applicationContext)

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get()=_tasks

    private val _tasksByEstateId = MutableLiveData<List<Task>>()
    val tasksByEstateId: LiveData<List<Task>> get() = _tasksByEstateId

    private val _getWorkersByTaskId = MutableLiveData<List<Int>>()
    val getWorkersByTaskId: LiveData<List<Int>> get() = _getWorkersByTaskId


    fun refreshData(){
        _tasks.value = taskDataProvider.getAllTasks()
    }

    fun insertTask(task: Task){
        taskDataProvider.insertTask(task)
    }

    fun updateTask(taskId: Int, taskName: String, taskDescription: String, taskType: String, taskAssignDate: String, taskDueDate: String, taskEstateId: Int): Int {
        return taskDataProvider.updateTask(taskId,taskName,taskDescription,taskType,taskAssignDate,taskDueDate,taskEstateId)
    }

    fun deleteTask(taskId: Int): Int {
        return taskDataProvider.deleteTask(taskId)
    }

    fun deleteTaskAssignmentByTaskId(taskId: Int): Boolean {
        return taskDataProvider.deleteTaskAssignmentByTaskId(taskId)
    }

    fun getTasksByEstateId(estateId: Int?) {
        val orders = taskDataProvider.getTasksByEstateId(estateId)
        _tasksByEstateId.postValue(orders) // Post the data to LiveData
    }

    fun getWorkersByEstateId(estateId: Int): LiveData<List<Worker>> {
        val workersLiveData = MutableLiveData<List<Worker>>()
        workersLiveData.value = taskDataProvider.getWorkersByEstateId(estateId) // Assuming this method returns a list of workers
        return workersLiveData
    }

    fun getAllWorkersByEstate(estateId: Int): LiveData<List<Worker>> {
        val workersLiveData = MutableLiveData<List<Worker>>()
        workersLiveData.value = taskDataProvider.getAllWorkersByEstate(estateId)  // Pass the estateId here
        return workersLiveData
    }


    fun assignTaskToWorker(taskId: Int, workerId: Int) {
        taskDataProvider.assignTaskToWorker(taskId,workerId)
    }

    fun getWorkersByTaskId(taskId: Int): List<Int> {
        val workers = taskDataProvider.getWorkersByTaskId(taskId)
        _getWorkersByTaskId.postValue(workers)
        return workers
    }

    fun removeWorkerFromTask(taskId: Int, workerId: Int) {
        taskDataProvider.removeWorkerFromTask(taskId,workerId)
    }


    fun getEmployeeNamesByEstateId(estateId: Int): List<String> {
        return taskDataProvider.getEmployeeNamesByEstateId(estateId)
    }

    fun getEmployeeNameById(employeeId: Int?): String {
        return taskDataProvider.getEmployeeNameById(employeeId)
    }

    fun updateTaskChallenges(taskId: Int, newChallenges: String): Int {
        return taskDataProvider.updateTaskChallenges(taskId,newChallenges)
    }

    fun updateTaskProgress(taskId: Int, newProgress: String) {
        taskDataProvider.updateTaskProgress(taskId,newProgress)
    }

    fun updateTaskSolution(taskId: Int, taskSolution: String): Int {
        return taskDataProvider.updateTaskSolution(taskId,taskSolution)
    }


}