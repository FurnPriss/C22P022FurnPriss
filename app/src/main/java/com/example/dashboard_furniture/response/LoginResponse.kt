package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("access_token")
	val accessToken: String,

	@field:SerializedName("refresh_token")
	val refreshToken: String
)
