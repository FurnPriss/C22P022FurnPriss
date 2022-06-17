package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class PredictProductResponse(

	@field:SerializedName("depth")
	val depth: Int,

	@field:SerializedName("cost")
	val cost: Int,

	@field:SerializedName("material")
	val material: String,

	@field:SerializedName("price")
	val price: Double,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("stock")
	val stock: Int,

	@field:SerializedName("height")
	val height: Int
)
