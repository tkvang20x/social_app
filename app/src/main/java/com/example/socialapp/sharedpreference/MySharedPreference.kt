package com.example.socialapp.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class MySharedPreference {
    var Shared_Reference: String = "shared_reference"
    private val KEY = "key"
    lateinit var mContext: Context

    constructor(mContext: Context) {
        this.mContext = mContext
    }

    fun putToken(value: String) {
        val sharedPreference: SharedPreferences =
            mContext.getSharedPreferences(Shared_Reference, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(KEY, value)
        editor.apply()

    }

    fun getToken(): String? {
        val sharedPreference: SharedPreferences =
            mContext.getSharedPreferences(Shared_Reference, Context.MODE_PRIVATE)
        return sharedPreference.getString(KEY, "")
    }

    fun clearToken() {
        val sharedPreference: SharedPreferences =
            mContext.getSharedPreferences(Shared_Reference, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
    }
}