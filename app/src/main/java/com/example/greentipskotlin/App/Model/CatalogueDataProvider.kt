package com.example.greentipskotlin.App.Model

import GreentipsDatabaseHelper
import android.content.Context

class CatalogueDataProvider(context: Context) {

    private val greentipsDatabaseHelper = GreentipsDatabaseHelper(context)

    fun insertCatalogueItem(catalogue: Catalogue){
        greentipsDatabaseHelper.insertCatalogueItem(catalogue)
    }

}