package com.example.greentipskotlin.App.Model

data class Catalogue(

    val Catalogue_Id:Int? = null,
    val Catalogue_Name:String,
    val Catalogue_Item_Type: String,
    val Catalogue_Item_Price: Double?,
    val Catalogue_Item_Quantity: Int,
    val Catalogue_Item_Description: String?,
    val Catalogue_Item_Availability:String,
    val Catalogue_Item_Image: String? = null

)
