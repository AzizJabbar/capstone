package com.bangkit.capstone.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
    @SerializedName("id")
    var userId: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("fullName")
    var fullName: String? = null,

    @SerializedName("gender")
    var gender: String? = null,

    @SerializedName("date_of_birth")
    var date_of_birth: String? = null,

    @SerializedName("height")
    var height: Int? = null,

    @SerializedName("weight")
    var weight: Int? = null,

    @SerializedName("token")
    var token: String? = null

): Parcelable

@Parcelize
data class AuthApiResponse(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("data")
    var data: UserModel? = null

): Parcelable

