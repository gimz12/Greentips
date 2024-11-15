package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class ResourcesDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)


    fun insertResources(resources: Resources){
        greentipsDatabaseHelper.insertResource(resources)
    }

    fun getAllResources():List<Resources>{
        return greentipsDatabaseHelper.getAllResources()
    }

    fun updateResource(resource: Resources) {
        greentipsDatabaseHelper.updateResource(resource)
    }

    fun deleteResource(resourceId: Int) {
        greentipsDatabaseHelper.deleteResource(resourceId)

    }

}