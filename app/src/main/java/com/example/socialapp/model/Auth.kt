package com.example.socialapp.model

import com.example.socialapp.model.auth.Email
import com.example.socialapp.model.auth.Phone

data class Auth(
        val email: Email,
        val  phone_number:Phone
) {
}