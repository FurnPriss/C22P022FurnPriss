package com.example.dashboard_furniture.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dashboard_furniture.Fragment.BottomSheetStockIn
import com.example.dashboard_furniture.Fragment.BottomSheetStockOut
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.ActivityDetailProductBinding
import java.math.RoundingMode
import java.text.DecimalFormat


class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var bottomSheetStockIn: BottomSheetStockIn
    private lateinit var bottomSheetStockOut: BottomSheetStockOut

    private var addStock: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "DETAIL PRODUCT"

        bottomSheetStockIn = BottomSheetStockIn()
        bottomSheetStockOut = BottomSheetStockOut()

        val categoryProduct = intent.getStringExtra(ResultPredictionActivity.EXTRA_CATEGORY_PRODUCT)
        val idProduct = intent.getStringExtra(ResultPredictionActivity.EXTRA_ID_PRODUCT)
        val stock = intent.getIntExtra(ResultPredictionActivity.EXTRA_STOCK, 0)
        val pricePrediction = intent.getDoubleExtra(ResultPredictionActivity.EXTRA_PRICE_PREDICTION, 0.0)

        val bundle = Bundle()
        bundle.putString(ResultPredictionActivity.EXTRA_CATEGORY_PRODUCT, categoryProduct)
        bundle.putString(ResultPredictionActivity.EXTRA_ID_PRODUCT, idProduct)
        bundle.putInt(ResultPredictionActivity.EXTRA_STOCK, stock)
        bundle.putDouble(ResultPredictionActivity.EXTRA_PRICE_PREDICTION, pricePrediction)


        val df = DecimalFormat("#")
        df.roundingMode = RoundingMode.UP
        binding.tvCategoriesProduct.text = categoryProduct
        binding.tvIdProduct.text = idProduct
        binding.tvStock.text = stock.toString()
        binding.tvPricePrediction.setText("Rp. "+df.format(pricePrediction)+"/Unit")

        binding.apply {
            if (categoryProduct == "Beds"){
                photoProduct.setImageResource(R.drawable.ic_bed)
            }else if (categoryProduct == "Tables & Desks"){
                photoProduct.setImageResource(R.drawable.ic_table)
            }else if (categoryProduct == "Wardrobes"){
                photoProduct.setImageResource(R.drawable.ic_drawer)
            }else if (categoryProduct == "Chairs"){
                photoProduct.setImageResource(R.drawable.ic_chair)
            }
        }

        binding.btnStockIn.setOnClickListener {
            bottomSheetStockIn.show(supportFragmentManager, "TAG")


            }

            /*
               Intent(this, ResultPredictionActivity::class.java).also {
                it.putExtra(StockInActivity.EXTRA_ID_PRODUCT, idProduct)
                startActivity(it)
                }
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView = layoutInflater.inflate(R.layout.bs_stock_in, null)

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
            */


        binding.btnStockOut.setOnClickListener {
            bottomSheetStockOut.show(supportFragmentManager, "TAG")

            }
            /*
           Intent(this, StockOutActivity::class.java).also {
                it.putExtra(StockOutActivity.EXTRA_ID_PRODUCT, idProduct)
                startActivity(it)
            val bottomSheetDialog = BottomSheetDialog(this)
            val bottomSheetView = layoutInflater.inflate(R.layout.bs_stock_out, null)


            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()*/

        }


    companion object{
        const val EXTRA_CATEGORY_PRODUCT = "extra_categoryProduct"
        const val EXTRA_ID_PRODUCT= "extra_idProduct"
        const val EXTRA_STOCK = "extra_stock"
        const val EXTRA_PRICE_PREDICTION = "extra_pricePrediction"
    }
}