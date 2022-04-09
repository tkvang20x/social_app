package com.example.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.AvatarUpload
import com.example.socialapp.model.DataUser
import com.example.socialapp.model.User
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val api: Api
    var data1: MutableLiveData<User>
     var dataAvatar: MutableLiveData<AvatarUpload>
    var token: String? = Const.TOKEN

    init {
        data1 = MutableLiveData<User>()
        dataAvatar = MutableLiveData<AvatarUpload>()
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api::class.java) as Api
    }
    fun getDataUser() {
        api.getUser("Bearer $token")?.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.body() == null ) {
                    data1.postValue(null)
                } else {
                    data1.postValue(response.body())
                    Log.d("data", data1.toString())
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }

    fun updateDataUser(dataUser: DataUser?) {
        api.updateUser("Bearer $token", dataUser)?.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                data1.setValue(response.body())
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Log.d("Error", "Fail: \$response.code()")
            }
        })
    }

    fun uploadAvatar(userId: String?, image: MultipartBody.Part?) {
        api.uploadAvatar("Bearer $token", userId, image)?.enqueue(object : Callback<AvatarUpload?> {
            override fun onResponse(call: Call<AvatarUpload?>, response: Response<AvatarUpload?>) {
                if (response.body() == null) {
                    dataAvatar.setValue(null)
                } else {
                    dataAvatar.setValue(response.body())
                }
            }

            override fun onFailure(call: Call<AvatarUpload?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }
    fun getDataById(userId: String?) {
        api.getProfileById("Bearer $token", userId)!!.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if (response.body() == null) {
                    data1.postValue(null)
                } else {
                    data1.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }



}