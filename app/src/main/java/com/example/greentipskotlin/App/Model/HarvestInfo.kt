package com.example.greentipskotlin.App.Model

data class HarvestInfo(
    val harvestID:Int? = null,
    val harvestType:String,
    val harvestDate:String,
    val harvestQuantity:Int,
    val harvestAddtional_Info:String,
    val harvestEstate:Int
)
