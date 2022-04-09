package com.example.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.DataProfile
import com.example.socialapp.model.Profile
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel:ViewModel() {

   val data2:MutableLiveData<Profile>
     private  val api:Api
    var token: String? = Const.TOKEN
    init {
        data2=MutableLiveData<Profile>()
        api=ApiRetrofit.createRetrofit(Const.BASE_URL,Api::class.java)
    }


    fun getDataProfile() {
        api.getProfile("Bearer $token")!!.enqueue(object : Callback<Profile?> {
            override fun onResponse(call: Call<Profile?>, response: Response<Profile?>) {
                if (response.body() == null || response.body()!!.data == null) {
                    data2.postValue(null)
                } else {
                    data2.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Profile?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }

    fun updateDataProfile(dataProfile: DataProfile?) {
        api.updateProfile("Bearer $token", dataProfile)!!.enqueue(object : Callback<Profile?> {
            override fun onResponse(call: Call<Profile?>, response: Response<Profile?>) {
                data2.value = response.body()
            }

            override fun onFailure(call: Call<Profile?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }
    fun getProfileById(userId: String?) {
        api.getMoreProfileById("Bearer $token", userId)!!.enqueue(object : Callback<Profile?> {
            override fun onResponse(call: Call<Profile?>, response: Response<Profile?>) {
                if (response.body() == null   ) {
                    data2.postValue(null)
                } else {
                    data2.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Profile?>, t: Throwable) {
                Log.d("Error", "Fail: \${response.code()}")
            }
        })
    }
}