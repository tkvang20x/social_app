package com.example.socialapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.socialapp.Const
import com.example.socialapp.model.PostX
import com.example.socialapp.model.comment.Comment
import com.example.socialapp.model.comment.PostComment
import com.example.socialapp.response.Api
import com.example.socialapp.response.ApiRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel :ViewModel() {
    private val api: Api
    var token: String = Const.TOKEN
    var comment :MutableLiveData<List<PostX>>
    var commentsPage :MutableLiveData<List<PostX>>
    var isCheckCmt: MutableLiveData<Boolean>
    init {
        api =ApiRetrofit.createRetrofit(Const.BASE_URL,Api ::class.java)
        comment=MutableLiveData()
        commentsPage= MutableLiveData()
        isCheckCmt =MutableLiveData()
    }

    fun getComments(id: String?) {
        api.getCommentsPage( "Bearer $token", id)!!.enqueue(object : Callback<Comment?> {
                override fun onResponse(call: Call<Comment?>, response: Response<Comment?>) {
                    if (response.body() == null) {
                        comment.setValue(null)
                    } else {
                        comment.setValue(response.body()!!.data.comments)
                    }
                }

                override fun onFailure(call: Call<Comment?>, t: Throwable) {
                    comment.setValue(null)
                }
            })
    }

//    fun getCommentsPage(id: String?, page: String?) {
//        commentsPage = MutableLiveData<List<PostX>>()
//        api.getCommentsPage("Bearer $token", id, page)!!.enqueue(object : Callback<Comment?> {
//                override fun onResponse(call: Call<Comment?>, response: Response<Comment?>) {
//                    if (response.body() == null) {
//                        commentsPage.setValue(null)
//                    } else {
//                        commentsPage.setValue(response.body()!!.data.comments)
//                    }
//                }
//
//                override fun onFailure(call: Call<Comment?>, t: Throwable) {
//                    commentsPage.setValue(null)
//                }
//            })
//    }

    fun postComment(comment: PostComment?) {
        api.postComment("Bearer $token", comment)!!.enqueue(object :Callback<Comment?>{
            override fun onResponse(call: Call<Comment?>, response: Response<Comment?>) {
                if (response.body()?.success==true) {
                    isCheckCmt.setValue(true)
                }else{
                    isCheckCmt.setValue(false)
                }
            }

            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                isCheckCmt.value = false
            }

        })
    }
}