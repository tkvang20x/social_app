package com.example.socialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.Post
import com.example.socialapp.model.PostComplete
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostDetailViewModel :ViewModel() {
    private val api: Api
    var token: String = Const.TOKEN
    var post:MutableLiveData<PostComplete>
    init {
        api= ApiRetrofit.createRetrofit(Const.BASE_URL,Api::class.java)
        post=MutableLiveData<PostComplete>()
    }

    fun getPostDetail(id: String?) {
        api.getPostDetail("Bearer $token", id)!!.enqueue(object : Callback<PostComplete?> {
                override fun onResponse(call: Call<PostComplete?>, response: Response<PostComplete?>) {
                    if (response.body() == null) {
                        post.setValue(null)
                    } else {
                        post.setValue(response.body())
                    }
                }

                override fun onFailure(call: Call<PostComplete?>, t: Throwable) {
                    post.setValue(null)
                }
            })
    }
}