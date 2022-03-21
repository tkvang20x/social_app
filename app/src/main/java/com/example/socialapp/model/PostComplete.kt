package com.example.socialapp.model

data class PostComplete(
    var success: Boolean,
    var message: String,
    var data: PostX,
    var statusCode: Int
) {
}