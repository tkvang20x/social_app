package com.example.socialapp.model.creatpost

import com.example.socialapp.model.Content

data class CreatePost(
    val content: Content,
    val type: String = "POST"
) {
}