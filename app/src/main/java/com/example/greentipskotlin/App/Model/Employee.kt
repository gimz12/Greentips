package com.example.greentipskotlin.App.Model

import java.util.Date

data class Employee(
    val employeeId: Int? = null,
    val employeeName: String?,
    val phoneNumber: String?,
    val address: String?,
    val gender: String?,
    val joinDate: Date? = null,  // Nullable Date
    val dateOfBirth: Date? = null,  // Nullable Date
    val age: Int?,
    val username: String?,
    val email: String?,
    val password: String?,
    val employeePositionId: Int?,
    val profileImage: String? = null  // Nullable String
)

