package com.example.dashboard_furniture.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.ActivityStockInBinding
import com.example.dashboard_furniture.databinding.ActivityStockOutBinding
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.RegisterViewModel
import com.example.dashboard_furniture.viewmodel.StockOutViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

class StockInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockInBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = TokenPreferences.getInstance(dataStore)
        /*
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.isLoading.observe(this, {
            showLoading(it)


        })
        */

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