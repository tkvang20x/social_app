package com.example.socialapp.model

data class Profile(
    val success:Boolean,
    val message:String,
    val data :DataProfile,
    val statusCode:Int
) {
}