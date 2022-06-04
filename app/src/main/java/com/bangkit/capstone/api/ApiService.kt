package com.bangkit.capstone.api

import com.bangkit.capstone.model.AuthApiResponse
import com.bangkit.capstone.model.ChatInput
import com.bangkit.capstone.model.ChatPredictResponse
import com.bangkit.capstone.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    fun loginUser(
        @Body user: UserModel
    ): Call<AuthApiResponse>

    // utk format date of birth : yyyy-mm-dd
//    @FormUrlEncoded
//    @Headers("Content-type: application/json")
//    @POST("users")
//    fun registerUser(
//        @Field("username") username: String,
//        @Field("password") password: String,
//        @Field("fullName") fullName: String,
//        @Field("gender") gender: String,
//        @Field("height") height: Int,
//        @Field("weight") weight: Int,
//        @Field("date_of_birth") date_of_birth: String,
//    ): Call<AuthApiResponse>

    @POST("users")
    fun registerUser(
        @Body user: UserModel
    ): Call<AuthApiResponse>

    @FormUrlEncoded
    @POST("food/predict")
    fun predictFood(
        @Field("food_name") food_name: String,
        @Field("when") whenInt: String
    ): Call<AuthApiResponse>

    @POST("bot/chat/predict")
    fun predictChat(
        @Header("Authorization") token: String,
        @Body chat_content: ChatInput
    ): Call<ChatPredictResponse>

}