package com.example.dashboard_furniture.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dashboard_furniture.Activity.LoginActivity
import com.example.dashboard_furniture.MainActivity
import com.example.dashboard_furniture.R
import com.example.dashboard_furniture.databinding.FragmentInventoryBinding
import com.example.dashboard_furniture.databinding.FragmentProfileBinding
import com.example.dashboard_furniture.util.TokenPreferences
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_validation")

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    private lateinit var pref: TokenPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        (requireActivity() as MainActivity).supportActionBar?.title = "Profile Account"
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        pref = TokenPreferences.getInstance(requireContext().dataStore)


        binding.btnLogout.setOnClickListener {
            logout()
            Toast.makeText(requireContext(), "Keluar", Toast.LENGTH_SHORT).show()
            Intent(requireContext(), LoginActivity::class.java).also {
                startActivity(it)

            }
        }
    }

    fun logout() =  lifecycleScope.launch{
        pref.clearToken()
    }


}