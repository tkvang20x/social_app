package com.example.socialapp.model

import com.example.socialapp.model.school.HighSchool
import com.example.socialapp.model.school.University


data class DataProfile(
    var hight_school: HighSchool?,
    var university: University?,
    val _id: String?,
    val user_id: String?,
    val prize: Any?,
    val specialized: Any?,
    val skill: Any?,
    val created_at: String?,
    val updated_at: String?,
    val __v: Int?
) {
}