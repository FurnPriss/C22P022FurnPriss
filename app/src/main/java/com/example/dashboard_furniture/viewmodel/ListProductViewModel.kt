package com.example.dashboard_furniture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.DetailProductResponse
import com.example.dashboard_furniture.response.ProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListProductViewModel : ViewModel() {

    private val _listProduct = MutableLiveData<List<ProductItem>>()
    val listProduct: LiveData<List<ProductItem>> = _listProduct

    private val _valueProduct = MutableLiveData<Int>()
    val valueProduct: LiveData<Int> = _valueProduct

    private val _valueStock = MutableLiveData<Int>()
    val valueStock: LiveData<Int> = _valueStock



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getListProduct(token: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListProduct("Bearer $token")
        client.enqueue(object : Callback<DetailProductResponse> {
            override fun onResponse(
                call: Call<DetailProductResponse>,
                response: Response<DetailProductResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _listProduct.postValue(response.body()?.product)
                    _valueProduct.postValue(response.body()?.total)
                    _valueStock.postValue(response.body()?.allStock)
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailProductResponse>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListProductCategory(token: String, category: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListProductCategory("Bearer $token", category)
        client.enqueue(object : Callback<DetailProductResponse> {
            override fun onResponse(
                call: Call<DetailProductResponse>,
                response: Response<DetailProductResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _listProduct.postValue(response.body()?.product)
                    _valueProduct.postValue(response.body()?.total)
                    _valueStock.postValue(response.body()?.allStock)
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailProductResponse>, t: Throwable) {
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "StoryViewModel"
    }
}