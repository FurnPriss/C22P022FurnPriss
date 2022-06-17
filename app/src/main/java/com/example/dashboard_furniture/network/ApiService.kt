package com.example.dashboard_furniture.network


import com.example.dashboard_furniture.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("predict/")
    fun postPredictProduct(
        @Header("Authorization") Authorization: String,
        @Field("id_product") id:String,
        @Field("category") category:String?,
        @Field("material") material:String?,
        @Field("width") width:Float,
        @Field("height") height:Float,
        @Field("depth") depth:Float,
        @Field("stock") stock:Int,
        @Field("cost") cost:Int
    ): Call<PredictProductResponse>

    @GET("predict/{category}")
    fun getListProductCategory(
        @Header("Authorization") Authorization: String,
        @Path("category") category:String?
    ): Call<DetailProductResponse>

    @GET("predict/")
    fun getListProduct(
        @Header("Authorization") Authorization: String
    ): Call<DetailProductResponse>

    @FormUrlEncoded
    @POST("token/")
    fun postLogin(
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("register/")
    fun postRegister(
        @Field("username") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ):Call<RegisterResponse>

    @FormUrlEncoded
    @POST("products/{id_product}/stockout")
    fun postStockOut(
        @Header("Authorization") Authorization: String,
        @Path("id_product") id:String,
        @Field("removed_stock") removeStock:Int,
        @Field("your_price") yourPrice:Int,
        @Field("date") date:String
    ):Call<StockOutResponse>

    @FormUrlEncoded
    @POST("products/{id_product}/stockin")
    fun postStockIn(
        @Header("Authorization") Authorization: String,
        @Path("id_product") id:String,
        @Field("added_stock") addStock:Int
    ):Call<StockInResponse>
    @FormUrlEncoded
    @POST("/api/reset-psw/")
    fun forgotPassword(
        @Field("email")email: String,
        @Field("password")password: String,
        @Field("confirm_password")confirm_password:String
    ): Call<ForgotResponse>

}