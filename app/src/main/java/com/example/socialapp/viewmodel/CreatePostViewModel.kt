package com.example.socialapp.viewmodel


import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.ImageUpload
import com.example.socialapp.model.PostComplete
import com.example.socialapp.model.creatpost.CreatePost
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList

class CreatePostViewModel :ViewModel() {

     val api: Api
     var imageUpload: MutableLiveData<ImageUpload>
     var postSucces: MutableLiveData<PostComplete>
    var token: String = Const.TOKEN
    init {
        api =ApiRetrofit.createRetrofit(Const.BASE_URL,Api:: class.java)
        imageUpload = MutableLiveData()
        postSucces = MutableLiveData()
    }



    fun uploadImage(list: ArrayList<MultipartBody.Part>) {
        api.uploadImage("Bearer $token", list)?.enqueue(object : Callback<ImageUpload?> {
            override fun onResponse(call: Call<ImageUpload?>, response: Response<ImageUpload?>) {
                if (response.body() != null || response.body()!!.success) {
                    imageUpload.postValue(response.body())
                Log.d("111","${response.body().toString()}")
                } else {
                    imageUpload.postValue(null)

                }
            }

            override fun onFailure(call: Call<ImageUpload?>, t: Throwable) {
                imageUpload.postValue(null)
            }
        })
    }

    fun getImageUpload(imageUpload: ImageUpload?): ArrayList<String>? {
        val list = ArrayList<String>()
        if (imageUpload == null) {
            return null
        } else {
            for (i in 0 until imageUpload.data.size-1) {
                list.add(imageUpload.data[i].filename)
            }
        }
        return list
    }

    fun createPost(createPost: CreatePost?) {
        api.creatPost("Bearer $token", createPost)!!.enqueue(object : Callback<PostComplete?> {
                override fun onResponse(
                    call: Call<PostComplete?>,
                    response: Response<PostComplete?>
                ) {
                    if (response.body() == null || response.body()!!.data == null) {
                        postSucces.postValue(null)

                    } else {
                        postSucces.postValue(response.body())

                    }
                }

                override fun onFailure(call: Call<PostComplete?>, t: Throwable) {
                    postSucces.postValue(null)
                }
            })
    }
}