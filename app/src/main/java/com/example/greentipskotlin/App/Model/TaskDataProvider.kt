package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class TaskDataProvider(context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertTask(task: Task){
        greentipsDatabaseHelper.insertTask(task)
    }

    fun updateTask(task: Task): Int {
        return greentipsDatabaseHelper.updateTask(task)
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

}