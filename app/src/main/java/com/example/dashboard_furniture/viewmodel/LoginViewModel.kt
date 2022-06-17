package com.example.dashboard_furniture.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.LoginResponse
import com.example.dashboard_furniture.util.TokenPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val pref: TokenPreferences) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isTravel = MutableLiveData<Boolean>()
    val isTravel: LiveData<Boolean> = _isTravel

    fun userLogin(email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    saveTokenValidation(response.body()?.data?.accessToken.toString(), response.body()?.data?.refreshToken.toString())
                    _isTravel.value = true
                } else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message.toString()}")
            }
        })
    }

    fun saveTokenValidation(accesToken: String, refreshToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pref.saveTokenValidation(accesToken, refreshToken)
        }
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}