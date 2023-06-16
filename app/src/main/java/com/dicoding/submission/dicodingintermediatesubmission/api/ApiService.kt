package com.dicoding.submission.dicodingintermediatesubmission.api

import com.dicoding.submission.dicodingintermediatesubmission.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun userRegis(
        @Field("name") name: String,
        @Field ("email") email: String,
        @Field ("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password")password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") token: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ) : StoryResponse

    @GET("stories")
    fun getStoriesLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int
    ) : Call<StoryResponse>

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ) : Call<AddStoryResponse>

}