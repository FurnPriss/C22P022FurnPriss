package com.example.dashboard_furniture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.PredictProductResponse
import com.example.dashboard_furniture.util.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel(private val pref: TokenPreferences): ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isTravel = MutableLiveData<Boolean>()
    val isTravel: LiveData<Boolean> = _isTravel

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage

    private val _pricePrediction = MutableLiveData<Double>()
    val pricePrediction: LiveData<Double> = _pricePrediction


    fun predictProduct(token: String, id: String, category: String?, material: String?, width: Float, height: Float, depth: Float, stock: Int, cost: Int){
        
        _isLoading.value = true
        val client = ApiConfig.getApiService().postPredictProduct("Bearer $token", id, category, material, width, height, depth, stock, cost)
        client.enqueue(object : Callback<PredictProductResponse> {
            override fun onResponse(
                call: Call<PredictProductResponse>,
                response: Response<PredictProductResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    savePricePrediction(response.body()?.price)
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _responseMessage.value = "Gagal Melakukan Prediksi"
                }
            }

            override fun onFailure(call: Call<PredictProductResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }

    fun savePricePrediction(price: Double?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (price != null) {
                pref.savePricePrediction(price)
            }
        }
    }
    companion object{
        private const val TAG = "PredictViewModel"
    }



}