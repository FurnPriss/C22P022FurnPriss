package com.example.dashboard_furniture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.StockOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockOutViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage

    fun postStockOut(token: String, id: String, removeStock: Int, price: Int, date: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postStockOut("Bearer $token", id, removeStock, price, date)
        client.enqueue(object : Callback<StockOutResponse> {
            override fun onResponse(
                call: Call<StockOutResponse>,
                response: Response<StockOutResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _responseMessage.value = "Berhasil Mengeluarkan Stock"
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _responseMessage.value = "Gagal Mengeluarkan Stock"
                }
            }

            override fun onFailure(call: Call<StockOutResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }
    companion object{
        private const val TAG = "StockOutViewModel"
    }
}