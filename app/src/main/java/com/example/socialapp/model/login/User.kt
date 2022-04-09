package com.example.socialapp.model.login

data class User(
    val __v: Int,
    val _id: String,
    val auth: Auth,
    val avatar: String,
    val created_at: String,
    val email: String,
    val first_name: String,
    val full_name: String,
    val gender: String,
    val id: String,
    val last_name: String,
    val password: Any,
    val phone_number: String,
    val point: Int,
    val roles: List<String>,
    val updated_at: String,
    val username: String
)