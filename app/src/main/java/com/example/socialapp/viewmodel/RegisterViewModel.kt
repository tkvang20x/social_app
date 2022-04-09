package com.example.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.login.DataLogin
import com.example.socialapp.model.register.DataRegister
import com.example.socialapp.model.register.GetRegister
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val api: Api
    var dataRegister : MutableLiveData<GetRegister>
    init {
        api = ApiRetrofit.createRetrofit(Const.BASE_URL, Api ::class.java)
        dataRegister = MutableLiveData()
    }
    fun postRegister(mDataRegister: DataRegister){
        api.postRegister(mDataRegister)?.enqueue(object : Callback<GetRegister> {

            override fun onResponse(call: Call<GetRegister>, response: Response<GetRegister>) {
                dataRegister.postValue(response.body())
                Log.d("aaa","mot${response.body()?.message}")
            }

            override fun onFailure(call: Call<GetRegister>, t: Throwable) {
                Log.d("aaa","fail")
            }


        })
    }
}