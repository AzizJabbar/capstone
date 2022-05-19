package com.bangkit.capstone.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
    @SerializedName("userId")
    var userId: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("token")
    var token: String? = null

): Parcelable

