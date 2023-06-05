package com.farez.naturascan.ui.main.fragment.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.R
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.databinding.FragmentAccountBinding
import com.farez.naturascan.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")

class AccountFragment : Fragment() {
    private lateinit var userPref : DataStore<Preferences>
    private  var _binding : FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AccountViewModel
    private lateinit var vmFactory: AccountVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPref = requireContext().dataStore
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        setupViewModel()
        binding.logoutCardView.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupViewModel() {
        vmFactory = AccountVMFactory(UserPreferences.getInstance(userPref))
        viewModel = ViewModelProvider(this, vmFactory)[AccountViewModel::class.java]
    }
}