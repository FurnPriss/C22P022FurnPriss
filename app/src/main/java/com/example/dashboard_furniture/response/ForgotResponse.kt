package com.example.dashboard_furniture.response

import com.google.gson.annotations.SerializedName

data class ForgotResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("detail")
    val detail: String
)
