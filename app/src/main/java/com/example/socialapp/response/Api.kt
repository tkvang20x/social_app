package com.example.socialapp.response


import com.example.socialapp.model.*
import com.example.socialapp.model.comment.Comment
import com.example.socialapp.model.comment.PostComment
import com.example.socialapp.model.creatpost.CreatePost
import com.example.socialapp.model.login.DataLogin
import com.example.socialapp.model.login.Login
import com.example.socialapp.model.register.DataRegister
import com.example.socialapp.model.register.GetRegister
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface Api {
    @GET("/v1/users/me")
    fun getUser(@Header("Authorization") token: String?): Call<User?>?

    @GET("/v1/users/me/profile/")
    fun getProfile(@Header("Authorization") token: String?): Call<Profile?>?

    @POST("/v1/tutors/update")
    fun updateProfile(
        @Header("Authorization") token: String?,
        @Body dataProfile: DataProfile?
    ): Call<Profile?>?

    @POST("/v1/users/me/update")
    fun updateUser(@Header("Authorization") token: String?, @Body dataUser: DataUser?): Call<User?>?

    @GET("/v1/users/{id}")
    fun getProfileById(
        @Header("Authorization") token: String?,
        @Path("id") userId: String?
    ): Call<User?>?


    @GET("/v1/tutors/{id}")
    fun getMoreProfileById(
        @Header("Authorization") token: String?,
        @Path("id") userId: String?
    ): Call<Profile?>?

    @Multipart
    @POST("/v1/upload/avatar/{id}")
    fun uploadAvatar(
        @Header("Authorization") token: String?,
        @Path("id") userId: String?,
        @Part image: MultipartBody.Part?
    ): Call<AvatarUpload?>?

//    @GET("/v1/posts")
//    fun getDataPost(@Header("Authorization") token: String?): Call<Post?>?

    @GET("/v1/posts")
    fun getPostPage(@Header("Authorization") token: String?, @Query("page") page: String): Call<Post>

    @GET("/v1/posts/{id}")
    fun getUserPostsPage(@Header("Authorization") token: String?,@Path("id") id: String?, @Query("page") page: String?): Call<Post?>?

    @GET("/v1/posts/detail/{id}")
    fun getPostDetail(@Header("Authorization") token: String?, @Path("id") id: String?): Call<PostComplete?>?

    @POST("/v1/comments/upload")
    fun postComment(@Header("Authorization") token: String?, @Body postComment: PostComment?): Call<Comment?>?

    @GET("/v1/comments/{id}")
    fun getCommentsPage(@Header("Authorization") token: String?, @Path("id") id: String?): Call<Comment?>?

    @Multipart
    @POST("/v1/upload/images")
    fun uploadImage(@Header("Authorization") token: String?, @Part image: ArrayList<MultipartBody.Part>): Call<ImageUpload?>?

    @POST("/v1/posts/upload")
    fun creatPost(@Header("Authorization") token: String?, @Body createPost: CreatePost?): Call<PostComplete>?

    @GET("/v1/posts/like/{id}")
    fun getLikePost(@Header("Authorization") token: String?, @Path("id") id: String?): Call<Like?>?

    @GET("/v1/posts/dislike/{id}")
    fun getDislikePost(@Header("Authorization") token: String?, @Path("id") id: String?): Call<Like?>?

    @POST("/auth/login")
    fun postLogin(@Body login: Login) : Call<DataLogin>

    @POST("/auth/register")
    fun postRegister(@Body dataRegister: DataRegister) : Call<GetRegister>
}