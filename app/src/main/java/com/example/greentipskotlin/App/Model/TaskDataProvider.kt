package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class TaskDataProvider(context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertTask(task: Task){
        greentipsDatabaseHelper.insertTask(task)
    }

    fun updateTask(taskId: Int, taskName: String, taskDescription: String, taskType: String, taskAssignDate: String, taskDueDate: String, taskEstateId: Int): Int {
        return greentipsDatabaseHelper.updateTask(taskId,taskName,taskDescription,taskType,taskAssignDate,taskDueDate,taskEstateId)
    }

    fun deleteTask(taskId: Int): Int {
        return greentipsDatabaseHelper.deleteTask(taskId)
    }

    fun getAllTasks(): List<Task> {
        return greentipsDatabaseHelper.getAllTasks()
    }

    fun getTasksByEstateId(estateId: Int?): List<Task> {
        return greentipsDatabaseHelper.getTasksByEstateId(estateId)
    }

    fun assignTaskToWorker(taskId: Int, workerId: Int) {
        greentipsDatabaseHelper.assignTaskToWorker(taskId,workerId)
    }

    fun getWorkersByTaskId(taskId: Int): List<Int> {
        return greentipsDatabaseHelper.getWorkersByTaskId(taskId)
    }

    fun removeWorkerFromTask(taskId: Int, workerId: Int) {
        greentipsDatabaseHelper.removeWorkerFromTask(taskId,workerId)
    }

    fun getWorkersByEstateId(estateId: Int): List<Worker> {
        return greentipsDatabaseHelper.getWorkersByEstateId(estateId)
    }

    fun getEmployeeNamesByEstateId(estateId: Int): List<String> {
        return greentipsDatabaseHelper.getEmployeeNamesByEstateId(estateId)
    }

    fun getEmployeeNameById(employeeId: Int?): String {
        return greentipsDatabaseHelper.getEmployeeNameById(employeeId)
    }

    fun getAllWorkersByEstate(estateId: Int): List<Worker> {
        return greentipsDatabaseHelper.getAllWorkersByEstate(estateId)
    }

    fun updateTaskChallenges(taskId: Int, newChallenges: String): Int {
        return greentipsDatabaseHelper.updateTaskChallenges(taskId,newChallenges)
    }

    fun updateTaskProgress(taskId: Int, newProgress: String) {
        greentipsDatabaseHelper.updateTaskProgress(taskId,newProgress)
    }

    fun updateTaskSolution(taskId: Int, taskSolution: String): Int {
        return greentipsDatabaseHelper.updateTaskSolution(taskId,taskSolution)
    }

    fun deleteTaskAssignmentByTaskId(taskId: Int): Boolean {
        return greentipsDatabaseHelper.deleteTaskAssignmentByTaskId(taskId)
    }

    fun getTasksByEmpId(workerEmpId: Int): List<Task> {
        return greentipsDatabaseHelper.getTasksByEmpId(workerEmpId)
    }

    fun getCompletedTasksByEmpId(workerEmpId: Int): List<Task> {
        return greentipsDatabaseHelper.getCompletedTasksByEmpId(workerEmpId)
    }



}