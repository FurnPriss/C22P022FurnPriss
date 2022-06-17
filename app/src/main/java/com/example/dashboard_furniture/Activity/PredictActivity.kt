package com.example.dashboard_furniture.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.ActivityPredictBinding
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.PredictViewModel
import com.example.dashboard_furniture.viewmodel.PredictViewModelFactory
import kotlinx.coroutines.*
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

class PredictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPredictBinding

    private lateinit var viewModel: PredictViewModel


    private var categoryProduct: String? = null
    private var idProduct: String? = null
    private var categoryMaterial: String? = null
    private var stock: Int? = null
    private var height: Float? = null
    private var width: Float? = null
    private var depth: Float? = null
    private var cost: Int? = null
    private var pricePrediction: Double? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = TokenPreferences.getInstance(dataStore)

        viewModel =  ViewModelProvider(this, PredictViewModelFactory(pref)).get(
            PredictViewModel::class.java
        )

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        categoryProduct()
        categoryMaterial()

        val categories_material = resources.getStringArray(R.array.categories_material)
        val arrayAdapterMaterial = ArrayAdapter(this, R.layout.dropdown_item, categories_material)
        binding.dropdownMaterial.setAdapter(arrayAdapterMaterial)



        binding.btnPredict.setOnClickListener {

            val valueStock = binding.edtStock.text.toString().trim()
            val valueWidth = binding.edtWidth.text.toString().trim()
            val valueHeight = binding.edtHeight.text.toString().trim()
            val valueDepth = binding.edtDepth.text.toString().trim()
            val valueCost = binding.edtCost.text.toString().trim()

            idProduct = binding.edtIdproduct.text.toString().trim()
            stock = valueStock.toInt()
            width = valueWidth.toFloat()
            height = valueHeight.toFloat()
            depth = valueDepth.toFloat()
            cost = valueCost.toInt()

            pref.accesToken.asLiveData().observe(this, {
                if (it != null && it != "") {
                    predictProduct(it, idProduct!!, categoryProduct!!, categoryMaterial!!, width!!, height!!, depth!!, stock!!, cost!!)
                    Log.e("", "ACCESToken: $idProduct")
                }
            })

            pref.pricePrediction.asLiveData().observe(this, {
                if (it!=null){
                    Log.e("", "ACCESToken: $it")
                    pricePrediction = it
                    Intent(this, ResultPredictionActivity::class.java).also {
                        it.putExtra(ResultPredictionActivity.EXTRA_CATEGORY_PRODUCT, categoryProduct)
                        it.putExtra(ResultPredictionActivity.EXTRA_ID_PRODUCT, idProduct)
                        it.putExtra(ResultPredictionActivity.EXTRA_STOCK, stock)
                        it.putExtra(ResultPredictionActivity.EXTRA_WIDTH, width)
                        it.putExtra(ResultPredictionActivity.EXTRA_HEIGHT, height)
                        it.putExtra(ResultPredictionActivity.EXTRA_DEPTH, depth)
                        it.putExtra(ResultPredictionActivity.EXTRA_COST, cost)
                        it.putExtra(ResultPredictionActivity.EXTRA_CATEGORY_MATERIAL, categoryMaterial)
                        it.putExtra(ResultPredictionActivity.EXTRA_PRICE_PREDICTION, pricePrediction)
                        startActivity(it)
                    }
                }
            })

        }
    }

    private fun predictProduct(token: String, id: String, category: String, material: String, width: Float, height: Float, depth: Float, stock: Int, cost: Int){
        return  viewModel.predictProduct(token, id, category, material, width, height, depth, stock, cost)
    }

    private fun categoryProduct(){
        val categories_product = resources.getStringArray(R.array.categories_product)
        val arrayAdapterProduct = ArrayAdapter(this, R.layout.dropdown_item, categories_product)
        binding.dropdownProduct.setAdapter(arrayAdapterProduct)

        binding.dropdownProduct.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            if (item == categories_product[0]){
                categoryProduct = "Beds"
                binding.edtIdproduct.setText("B-")
            } else if (item == categories_product[1]){
                categoryProduct = "Chairs"
                binding.edtIdproduct.setText("C-")
            } else if (item == categories_product[2]){
                categoryProduct = "Tables & Desks"
                binding.edtIdproduct.setText("T-")
            } else if (item == categories_product[3]){
                categoryProduct = "Wardrobes"
                binding.edtIdproduct.setText("W-")
            }
        }
    }

    private fun categoryMaterial(){
        val categories_material = resources.getStringArray(R.array.categories_material)
        val arrayAdapterProduct = ArrayAdapter(this, R.layout.dropdown_item, categories_material)
        binding.dropdownMaterial.setAdapter(arrayAdapterProduct)

        binding.dropdownMaterial.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            if (item == categories_material[0]){
                categoryMaterial = "Plastic"
            } else if (item == categories_material[1]){
                categoryMaterial = "Steel"
            } else if (item == categories_material[2]){
                categoryMaterial = "Wood"
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }
    /*
    private fun savePrediction(price: Double){
        if(price != null){
            pricePrediction = price
        } else{
            Toast.makeText(this, "Tidak Tersimpan", Toast.LENGTH_SHORT).show()
        }
    }
    */


}