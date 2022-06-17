package com.example.dashboard_furniture.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.ActivityRegisterBinding
import com.example.dashboard_furniture.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener, View.OnKeyListener {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    private var message: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.edtName.onFocusChangeListener = this
        mBinding.edtEmail.onFocusChangeListener = this
        mBinding.edtPassword.onFocusChangeListener = this

        actionBar?.title = "REGISTER"

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        viewModel.responseMessage.observe(this, {
            message = it
        })


        mBinding.btnRegister.setOnClickListener { view ->
            val name = mBinding.edtName.text.toString().trim()
            val email = mBinding.edtEmail.text.toString().trim()
            val password = mBinding.edtPassword.text.toString().trim()

            viewModel.userRegister(name, email, password)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateName(): Boolean{
        var errorMessage: String? = null
        val value: String = mBinding.edtName.text.toString()
        if (value.isEmpty()){
            errorMessage = "Name Required and Must be Minimum 6 Characters"
        }
        else if(value != null && value.length < 6){
            errorMessage = "Name Must be Minimum 6 Characters"
        }
        if(errorMessage!= null){
            mBinding.notifname.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateEmail(): Boolean{
        var errorMessage: String? = null
        var value = mBinding.edtEmail.text.toString()
        if (value.isEmpty()){
            errorMessage = "Email Required"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Email Must be Correct"
        }
        if(errorMessage!= null){
            mBinding.notifmail.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        var value = mBinding.edtPassword.text.toString()
        if (value.isEmpty()){
            errorMessage = "Password Required"
        }else if(value.length < 6 ){
            errorMessage = "Password Minimum 8 Characters"
        }
        if(errorMessage!= null){
            mBinding.notifpass.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(view: View?) {

    }

    override fun onFocusChange(view: View?, focus: Boolean) {
        if(view != null){
            when(view.id){
                R.id.edt_name -> {
                    if(focus){
                        if(mBinding.notifname.isErrorEnabled){
                            mBinding.notifname.isErrorEnabled = false
                        }
                    }else{
                        if(validateName()){

                        }
                    }
                }
                R.id.edt_email -> {
                    if(focus){
                        if(mBinding.notifmail.isErrorEnabled){
                            mBinding.notifmail.isErrorEnabled = false
                        }
                    }else{
                        validateEmail()
                    }
                }
                R.id.edt_password -> {
                    if(focus){
                        if(mBinding.notifpass.isErrorEnabled){
                            mBinding.notifpass.isErrorEnabled = false
                        }
                    }else{
                        validatePassword()
                    }
                }
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        return false
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            mBinding.progressBar.visibility = View.VISIBLE
        } else{
            mBinding.progressBar.visibility = View.GONE
        }
    }


}