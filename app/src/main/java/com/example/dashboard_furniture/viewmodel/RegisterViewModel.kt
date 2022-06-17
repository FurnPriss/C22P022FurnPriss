package com.example.dashboard_furniture.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> = _responseMessage

    fun userRegister(name: String, email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _responseMessage.value = "bisa login"
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _responseMessage.value = "gagal login"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }
    companion object{
        private const val TAG = "RegisterViewModel"
    }
}