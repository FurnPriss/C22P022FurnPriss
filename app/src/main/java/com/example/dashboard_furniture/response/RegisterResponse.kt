package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("email")
	val email: List<String>
)
