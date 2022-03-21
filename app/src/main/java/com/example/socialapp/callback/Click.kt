package com.example.socialapp.callback

import com.example.socialapp.model.Profile

interface Click {

    fun onClick(position:Int)
    fun callBack(profile: Profile)
}