package com.example.socialapp.model.comment

import com.example.socialapp.model.Content

data class PostComment(
    val content: Content,
    val post_id:String
) {
}