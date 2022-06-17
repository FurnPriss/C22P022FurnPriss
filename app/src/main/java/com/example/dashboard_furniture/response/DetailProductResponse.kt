package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("product")
	val product: List<ProductItem>,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("all_stock")
	val allStock: Int
)

data class ProductItem(

	@field:SerializedName("id_product")
	val idProduct: String,

	@field:SerializedName("price")
	val price: Double,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("stock")
	val stock: Int
)
