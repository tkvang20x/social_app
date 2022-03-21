package com.example.socialapp.response


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofit {
    companion object{
        //tao retrofits s
        fun <T> createRetrofit(baseLink: String, clazz: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(baseLink)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clazz)
        }
    }

}