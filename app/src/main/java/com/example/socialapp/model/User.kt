package com.example.socialapp.model

import java.io.Serializable

data class User(
    var success: Boolean,
    var message: String,
    var data: DataUser,
    var statusCode: Int
):Serializable {


}