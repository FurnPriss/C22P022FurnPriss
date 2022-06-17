package com.example.dashboard_furniture.Activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dashboard_furniture.MainActivity
import com.example.dashboard_furniture.databinding.ActivityResultPredictionBinding
import com.example.dashboard_furniture.util.TokenPreferences
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

class ResultPredictionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultPredictionBinding


    private lateinit var pref: TokenPreferences

    private var pricePrediction: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPredictionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        pref = TokenPreferences.getInstance(dataStore)

        /*

        pref.pricePrediction.asLiveData().observe(this, {
            pricePrediction = it
        })

         */


        val categoryProduct = intent.getStringExtra(EXTRA_CATEGORY_PRODUCT)
        val idProduct = intent.getStringExtra(EXTRA_ID_PRODUCT)
        val stock = intent.getIntExtra(EXTRA_STOCK, 0)
        val width = intent.getFloatExtra(EXTRA_WIDTH, 0F)
        val height = intent.getFloatExtra(EXTRA_HEIGHT, 0F)
        val depth = intent.getFloatExtra(EXTRA_DEPTH, 0F)
        val cost = intent.getIntExtra(EXTRA_COST, 0)
        val categoryMaterial = intent.getStringExtra(EXTRA_CATEGORY_MATERIAL)
        val pricePrediction = intent.getDoubleExtra(EXTRA_PRICE_PREDICTION, 0.0)


        val bundle = Bundle()
        bundle.putString(EXTRA_CATEGORY_PRODUCT, categoryProduct)
        bundle.putString(EXTRA_ID_PRODUCT, idProduct)
        bundle.putInt(EXTRA_STOCK, stock)
        bundle.putFloat(EXTRA_WIDTH, width)
        bundle.putFloat(EXTRA_HEIGHT, height)
        bundle.putFloat(EXTRA_DEPTH, depth)
        bundle.putInt(EXTRA_COST, cost)
        bundle.putString(EXTRA_CATEGORY_MATERIAL, categoryMaterial)
        bundle.putDouble(EXTRA_PRICE_PREDICTION, pricePrediction)

        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.UP
        binding.tvCategoriesProduct.text = categoryProduct
        binding.tvIdProduct.text = idProduct
        binding.tvStock.text = stock.toString()
        binding.tvWidth.setText(df.format(width)+" cm")
        binding.tvHeight.setText(df.format(height)+" cm")
        binding.tvDepth.setText(df.format(depth)+" cm")
        binding.tvCost.setText("Rp. $cost")
        binding.tvCategoriesMaterial.text = categoryMaterial
        binding.tvPricePrediction.setText("Rp. "+df.format(pricePrediction)+"/unit")

        binding.btnSave.setOnClickListener {
            clearPrice()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    fun clearPrice() =  lifecycleScope.launch{
        pref.clearPrice()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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