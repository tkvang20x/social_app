package com.example.socialapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.login.DataLogin
import com.example.socialapp.model.login.Login
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    private val api: Api
     var dataLogin : MutableLiveData<DataLogin>
        init {
            api = ApiRetrofit.createRetrofit(Const.BASE_URL,Api ::class.java)
                dataLogin = MutableLiveData()
        }
    fun postLogin(login : Login){
        api.postLogin(login)?.enqueue(object : Callback<DataLogin>{
            override fun onResponse(call: Call<DataLogin>, response: Response<DataLogin>) {
                dataLogin.postValue(response.body())

            }

            override fun onFailure(call: Call<DataLogin>, t: Throwable) {

            }


        })
    }
}