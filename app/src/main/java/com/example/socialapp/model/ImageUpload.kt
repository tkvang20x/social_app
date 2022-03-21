package com.example.socialapp.model

data class ImageUpload(
    val success: Boolean,
    val message: String,
    val data: List<DataAvatar>,
    val statusCode: Int
) {
}