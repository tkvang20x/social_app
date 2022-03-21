package com.example.socialapp.model.comment

data class Comment(
    val success:Boolean,
    val mesage:String,
    val data:DataComment,
    val status:Int
) {
}