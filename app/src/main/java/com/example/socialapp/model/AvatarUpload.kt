package com.example.socialapp.model

data class AvatarUpload(
    val success:String,
    val message:String,
    val dataAvatar: DataAvatar,
    val statuscode:Int
) {
}