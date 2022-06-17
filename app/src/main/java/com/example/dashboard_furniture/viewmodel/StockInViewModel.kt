package com.example.dashboard_furniture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.StockInResponse
import com.example.dashboard_furniture.response.StockOutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockInViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage

    fun postStockIn(token: String,id: String, addedStock: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postStockIn("Bearer $token", id,addedStock)
        client.enqueue(object : Callback<StockInResponse> {
            override fun onResponse(
                call: Call<StockInResponse>,
                response: Response<StockInResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _responseMessage.value = "Berhasil Menambah Stock"
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _responseMessage.value = "Gagal Mengeluarkan Stock"
                }
            }

            override fun onFailure(call: Call<StockInResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }
    companion object{
        private const val TAG = "StockInViewModel"
    }
}