package com.farez.naturascan.ui.main.fragment.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.farez.naturascan.data.local.model.sampleArticleList
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.databinding.FragmentHomeBinding
import com.farez.naturascan.ui.login.LoginActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var vmFactory: HomeVMFactory
    private lateinit var userPref : DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userPref = requireContext().dataStore
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViewModel()
        setupView()
        return binding.root
    }


    private fun setupViewModel() {
        vmFactory = HomeVMFactory(UserPreferences.getInstance(userPref))
        viewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
    }

    private fun setupView() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.apply {
            val adapter = HomeAdapter(sampleArticleList.sampleArticle)
            homeRV.adapter = adapter
            homeRV.layoutManager = LinearLayoutManager(context)
            adapter.notifyDataSetChanged()

            logoutButton.setOnClickListener {
                viewModel.logout()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }
}