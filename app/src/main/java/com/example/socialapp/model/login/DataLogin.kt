package com.example.socialapp.model.login

data class DataLogin(
    val `data`: Data,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)