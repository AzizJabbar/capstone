package com.bangkit.capstone.model

import com.google.gson.annotations.SerializedName

data class FoodInput(

	@field:SerializedName("food_name")
	val foodName: String,

	@field:SerializedName("when")
	val `when`: String
)
