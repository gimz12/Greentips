package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class FieldManagerDataProvider(context: Context) {

    private val greentipsDatabaseHelper=GreentipsDatabaseHelper(context)

    fun insertFieldManager(fieldManager: FieldManager){
        greentipsDatabaseHelper.insertFieldManager(fieldManager)
    }

    fun getFieldManagerById(id: Int): FieldManager?{
        return greentipsDatabaseHelper.getFieldManagerById(id)
    }

    fun updateFieldManager(fieldManager: FieldManager): Int {
        return greentipsDatabaseHelper.updateFieldManager(fieldManager)
    }

}