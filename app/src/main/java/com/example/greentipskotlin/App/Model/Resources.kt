package com.example.greentipskotlin.App.Model

data class Resources(
    val resourcesID:Int? = null,
    val description:String,
    val date:String,
    val billNumber:String,
    val amount:Double,
    val resourcesEstate:Int
)
