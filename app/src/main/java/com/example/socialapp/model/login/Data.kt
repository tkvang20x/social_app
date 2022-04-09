package com.example.socialapp.model.login

data class Data(
    val access_token: String,
    val expires_in: Int,
    val user: User
)