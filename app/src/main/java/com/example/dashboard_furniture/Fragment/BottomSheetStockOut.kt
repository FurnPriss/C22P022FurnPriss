package com.example.dashboard_furniture.Fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.dashboard_furniture.MainActivity
import com.example.dashboard_furniture.databinding.BsStockOutBinding
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.StockOutViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetStockOut : BottomSheetDialogFragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

    private var _binding: BsStockOutBinding? = null
    private val binding get() = _binding!!

    private var stockOut: Int? = null
    private var price: Int? = null
    private var date: String? = null

    private var message: String? = null

    private lateinit var viewModel: StockOutViewModel
    private lateinit var pref: TokenPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BsStockOutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pref = TokenPreferences.getInstance(requireContext().dataStore)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            StockOutViewModel::class.java)

        viewModel.responseMessage.observe(this,{
            message = it
        })

        binding.btnCalendar.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            supportFragmentManager.setFragmentResultListener("REQUEST_KEY", viewLifecycleOwner){
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


            pref.accesToken.asLiveData().observe(this,{
                if (it!=null)
                stockOut(it, idProduct!!, stockOut!!, price!!, date!!)
            })
            Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))

        }


        // Inflate the layout for this fragment
        return root
    }

    private fun stockOut(token: String, id: String, removeStock: Int, yourPrice: Int, date: String){
        return  viewModel.postStockOut(token, id, removeStock, yourPrice, date)
    }

    companion object{
        const val EXTRA_ID_PRODUCT= "extra_idProduct"
    }


}