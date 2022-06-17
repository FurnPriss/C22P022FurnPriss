package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class StockOutResponse(

	@field:SerializedName("message")
	val message: String
)
