package com.bangkit.capstone.model

import com.google.gson.annotations.SerializedName

data class ChatPredictResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("predicted_label")
	val predictedLabel: String
)
