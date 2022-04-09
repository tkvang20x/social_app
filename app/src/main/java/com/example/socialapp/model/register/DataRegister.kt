package com.example.socialapp.model.register

import com.example.socialapp.model.auth.Phone

data class DataRegister (
     val username : String,
     val email : String,
     val phone_number: String,
     val first_name : String,
     val password : String
)