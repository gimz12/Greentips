package com.example.greentipskotlin.App.Model

data class Intercrops(
    val intercropId:Int? = null,
    val intercropType:String,
    val intercropPlantedDate:String,
    val intercropAddtionalInfo:String,
    val intercropEstateID:Int
)
