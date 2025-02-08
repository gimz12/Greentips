package com.example.greentipskotlin.App.Model

data class CoconutProductionReport(
    val estateId: Int,
    val plantDate: String,
    val numberOfCoconuts: Int,
    val treeAge: String,
    val treeHealth: String,
    val treeType: String,
    val additionalInfo: String
)
