package com.example.dashboard_furniture.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dashboard_furniture.databinding.ActivityForgotPasswordBinding
import com.example.dashboard_furniture.network.ApiConfig
import com.example.dashboard_furniture.response.ForgotResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener {
            val email = binding.edtEmailForgot.text.toString()
            val password = binding.edtNewPassword.text.toString()
            val confirm_password = binding.edtRepassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.edtEmailForgot.error = "Email Required"
                    binding.edtEmailForgot.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.edtNewPassword.error = "Password Required"
                    binding.edtNewPassword.requestFocus()
                    return@setOnClickListener
                }
                confirm_password.isEmpty() -> {
                    binding.edtRepassword.error = " Password Required"
                    binding.edtRepassword.requestFocus()
                    return@setOnClickListener
                }
                else -> forgotPassword(email, password, confirm_password)
            }

        }
    }


    private fun forgotPassword(email: String, password: String, confirm_password: String) {
        val client = ApiConfig.getApiService().forgotPassword(email, password, confirm_password)
            .enqueue(object :
                Callback<ForgotResponse> {
                override fun onResponse(
                    call: Call<ForgotResponse>,
                    response: Response<ForgotResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            response.body()?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            response.body()?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }


                }

                override fun onFailure(call: Call<ForgotResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }
}