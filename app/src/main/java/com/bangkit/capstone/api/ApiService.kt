package com.bangkit.capstone.api

import com.bangkit.capstone.model.AuthApiResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<AuthApiResponse>

    // utk format date of birth : yyyy-mm-dd
    @FormUrlEncoded
    @POST("users")
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("fullName") fullName: String,
        @Field("gender") gender: String,
        @Field("height") height: Int,
        @Field("weight") weight: Int,
        @Field("date_of_birth") date_of_birth: String,
    ): Call<AuthApiResponse>



}