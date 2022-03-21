package com.example.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.Like
import com.example.socialapp.model.Post
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class PostViewModel :ViewModel() {

    private val api:Api
    var dataPost:MutableLiveData<Post>
    var token: String = Const.TOKEN

    init {
        api=ApiRetrofit.createRetrofit(Const.BASE_URL,Api::class.java)
        dataPost= MutableLiveData<Post>()
    }

    fun getPostFirst(page:String){
        api.getPostPage("Bearer $token",page)?.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
               if(response.body() ==null){
                   dataPost.postValue(null)
               }else{
                   dataPost.postValue(response.body())
               }
            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                Log.d("Error", "Fail: \$response.code()")
            }

        })
    }

    fun getPostPage(page:String){
        dataPost= MutableLiveData()
        api.getPostPage("Bearer $token",page)?.enqueue(object : Callback<Post?> {
            override fun onResponse(call: Call<Post?>, response: Response<Post?>) {
                if(response.body() ==null){
                    dataPost.postValue(null)
                }else{
                    dataPost.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Post?>, t: Throwable) {
                Log.d("Error", "Fail: \$response.code()")
            }

        })
    }

    fun getLikePost(id: String?) {
        api.getLikePost("Bearer $token", id)!!
            .enqueue(object : Callback<Like?> {
                override fun onResponse(call: Call<Like?>, response: Response<Like?>) {
                    if (response.body() == null) {
                        Log.d("likenull", "onResponse: NULL")
                    } else {
                        Log.d("like", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Like?>, t: Throwable) {
                    Log.d("like fail", "onFailure: ")
                }
            })
    }

    fun getDislikePost(id: String?) {
        api.getDislikePost("Bearer $token", id)!!.enqueue(object : Callback<Like?> {
                override fun onResponse(call: Call<Like?>, response: Response<Like?>) {
                    if (response.body() == null) {
                        Log.d("dislike null", "onResponse: NULL")
                    } else {
                        Log.d("dislike", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<Like?>, t: Throwable) {
                    Log.d("dislike fail", "onFailure: ")
                }
            })
    }


}