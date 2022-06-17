package com.example.dashboard_furniture.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.dashboard_furniture.MainActivity
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.BsStockInBinding
import com.example.dashboard_furniture.databinding.FragmentInventoryBinding
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.StockInViewModel
import com.example.dashboard_furniture.viewmodel.StockOutViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetStockIn : BottomSheetDialogFragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

    private var _binding: BsStockInBinding? = null
    private val binding get() = _binding!!

    private var addStock: Int? = null

    private lateinit var viewModel: StockInViewModel
    private lateinit var pref: TokenPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BsStockInBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pref = TokenPreferences.getInstance(requireContext().dataStore)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            StockInViewModel::class.java)

        binding.btnSaveStockIn.setOnClickListener {
            val valueAddStock = binding.edtAddstock.text.toString().trim()
            val idProduct = binding.edtIdproduct.text.toString().trim()

            addStock = valueAddStock.toInt()

            pref.accesToken.asLiveData().observe(this,{
                if (it!=null)
                    stockIn(it,idProduct!!, addStock!!)
            })

            startActivity(Intent(requireContext(), MainActivity::class.java))
        }


        // Inflate the layout for this fragment
        return root
    }

    private fun stockIn(token: String,id: String, addedStock: Int){
        return  viewModel.postStockIn(token,id, addedStock)
    }


}