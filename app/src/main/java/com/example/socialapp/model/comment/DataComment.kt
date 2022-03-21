package com.example.socialapp.model.comment

import com.example.socialapp.model.PostX

data class DataComment(
    val data: List<Any>,
    val next_page:String,
    val prev_page:String,
    val comments :List<PostX>
) {
}