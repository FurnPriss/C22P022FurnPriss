package com.example.dashboard_furniture.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard_furniture.Activity.DetailProductActivity
import com.example.dashboard_furniture.MainActivity
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.adapter.ListProductAdapter
import com.example.dashboard_furniture.databinding.FragmentInventoryBinding
import com.example.dashboard_furniture.response.ProductItem
import com.example.dashboard_furniture.util.TokenPreferences
import com.example.dashboard_furniture.viewmodel.ListProductViewModel


class InventoryFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

    private var _binding: FragmentInventoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapters: ListProductAdapter
    private lateinit var viewModel: ListProductViewModel
    private lateinit var pref: TokenPreferences

    private var categoryProduct: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).supportActionBar?.title = "Inventory Product"
        val root: View = binding.root
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.title = "Inventory Product"

        pref = TokenPreferences.getInstance(requireContext().dataStore)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ListProductViewModel::class.java)

        val categories_product = resources.getStringArray(R.array.categories_product)
        val arrayAdapterProduct = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories_product)
        binding.dropdownProduct.setAdapter(arrayAdapterProduct)



        viewModel.isLoading.observe(requireActivity(), {
            showLoading(it)
        })

        viewModel.valueProduct.observe(requireActivity(), {
            binding.tvTotalProduct.setText("$it")

        })
        viewModel.valueStock.observe(requireActivity(), {
            binding.tvStockInProduct.setText("$it")
        })

        adapters = ListProductAdapter()

        adapters.setOnItemClicked(object : ListProductAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: ProductItem) {
                Intent(requireContext(), DetailProductActivity::class.java).also {
                    it.putExtra(DetailProductActivity.EXTRA_CATEGORY_PRODUCT, data.category)
                    it.putExtra(DetailProductActivity.EXTRA_ID_PRODUCT, data.idProduct)
                    it.putExtra(DetailProductActivity.EXTRA_STOCK, data.stock)
                    it.putExtra(DetailProductActivity.EXTRA_PRICE_PREDICTION, data.price)
                    startActivity(it)
                }
            }
        })

        binding.rvListproduct.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapters
        }



        binding.dropdownProduct.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            if (item == categories_product[0]){
                categoryProduct = "Beds"
                pref.accesToken.asLiveData().observe(viewLifecycleOwner, {
                    if (it != null) {
                        showListProductCategory(it, categoryProduct)
                    }
                })
            } else if (item == categories_product[1]){
                categoryProduct = "Chairs"
                pref.accesToken.asLiveData().observe(viewLifecycleOwner, {
                    if (it != null) {
                        showListProductCategory(it, categoryProduct)
                    }
                })
            } else if (item == categories_product[2]){
                categoryProduct = "Tables & Desks"
                pref.accesToken.asLiveData().observe(viewLifecycleOwner, {
                    if (it != null) {
                        showListProductCategory(it, categoryProduct)
                    }
                })
            } else if (item == categories_product[3]){
                categoryProduct = "Wardrobes"
                pref.accesToken.asLiveData().observe(viewLifecycleOwner, {
                    if (it != null) {
                        showListProductCategory(it, categoryProduct)
                    }
                })
            }
        }
        pref.accesToken.asLiveData().observe(viewLifecycleOwner, {
            if (it != null) {
                showListProduct(it)
            }
        })
        viewModel.listProduct.observe(viewLifecycleOwner, {
            if(it!= null){
                adapters.setListProduct(it)
            }
        })
    }

    private fun showListProduct(token: String) {
        return viewModel.getListProduct(token)
    }

    private fun showListProductCategory(token: String, category: String?) {
        return viewModel.getListProductCategory(token, category)
    }


    private fun showLoading(isLoading: Boolean){
        if(isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else{
            binding.progressBar.visibility = View.GONE
        }
    }

}