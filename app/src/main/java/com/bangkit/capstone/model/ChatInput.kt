package com.bangkit.capstone.model

import com.google.gson.annotations.SerializedName

data class ChatInput(

	@field:SerializedName("chat_content")
	val chatContent: String
)
