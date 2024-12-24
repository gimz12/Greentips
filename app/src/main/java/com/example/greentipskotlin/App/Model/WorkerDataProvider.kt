package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class WorkerDataProvider(context: Context)
{
    private val greentipsDatabaseHelper =GreentipsDatabaseHelper(context)

    fun insertWorker(worker: Worker){
        greentipsDatabaseHelper.insertWorker(worker)
    }

    fun getWorkerById(id :Int):Worker?{
        return greentipsDatabaseHelper.getWorkerById(id)
    }

    fun updateWorker(worker: Worker): Int {
        return greentipsDatabaseHelper.updateWorker(worker)
    }

}