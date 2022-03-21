package com.example.socialapp.model

data class DataUser(
    var first_name:String,
    var last_name:String,
    var gender:String,
    val auth: Auth?,
    val roles: List<Any>?,
    val avatar:String?,
    val point:Int?,
    val _id:String?,
    val username:String?,
    val email:String?,
    val password:Any?,
    val created_at:String?,
    val updated_at:String?,
    val __v:Int?,
    val full_name:String?,
    val total_followers:Int?,
    val total_followings:Int?,
    val id:String?
) {
}