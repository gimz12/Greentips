package com.example.greentipskotlin.App.Model

import android.hardware.SensorAdditionalInfo

data class Fertilizer(
    val fertilizerId: Int? = null,
    val fertilizerName:String,
    val fertilizerType:String,
    val fertilizerDate:String,
    val fertilizerQuantity:String,
    val fertilizerComposition:String,
    val fertilizerAdditionalInfo:String,
    val fertilizerEstateId:Int
)
