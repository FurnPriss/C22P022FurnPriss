package com.example.dashboard_furniture.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.dashboard_furniture.Fragment.DatePickerFragment
import com.example.dashboard_furniture.databinding.ActivityLoginBinding
import com.example.dashboard_furniture.databinding.ActivityStockOutBinding
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.RegisterViewModel
import com.example.dashboard_furniture.viewmodel.StockOutViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

class StockOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockOutBinding

    private lateinit var viewModel: StockOutViewModel

    private var stockOut: Int? = null
    private var price: Int? = null
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = TokenPreferences.getInstance(dataStore)

        viewModel = ViewModelProvider(this).get(StockOutViewModel::class.java)
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })
        binding.btnCalendar.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = this.supportFragmentManager

            supportFragmentManager.setFragmentResultListener("REQUEST_KEY", this){
                    resultKey, bundle -> if (resultKey == "REQUEST_KEY"){
                val dateSelected = bundle.getString("SELECTED_DATE")
                binding.autotvDate.setText(dateSelected)
                date = dateSelected
            }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }
        binding.btnSaveStockOut.setOnClickListener {
            val valueStockOut = binding.edtStockOut.text.toString().trim()
            val valuePrice = binding.edtPrice.text.toString().trim()
            val idProduct = binding.edtIdproduct.text.toString().trim()


            stockOut = valueStockOut.toInt()
            price = valuePrice.toInt()

            pref.accesToken.asLiveData().observe(this, {
                if (it!=null){
                    stockProduct(it, idProduct!!, stockOut!!, price!!, date!!)
                }

            })


            Toast.makeText(this, "dat"+date, Toast.LENGTH_SHORT).show()
        }
    }
    private fun stockProduct(token: String, id: String, removeStock: Int, yourPrice: Int, date: String){
        return  viewModel.postStockOut(token, id, removeStock, yourPrice, date)
    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_CATEGORY_PRODUCT = "extra_categoryProduct"
        const val EXTRA_ID_PRODUCT= "extra_idProduct"
        const val EXTRA_STOCK = "extra_stock"
        const val EXTRA_WIDTH = "extra_width"
        const val EXTRA_HEIGHT = "extra_height"
        const val EXTRA_DEPTH = "extra_depth"
        const val EXTRA_COST = "extra_cost"
        const val EXTRA_CATEGORY_MATERIAL = "extra_categoryMaterial"
        const val EXTRA_PRICE_PREDICTION = "extra_pricePrediction"
    }
}