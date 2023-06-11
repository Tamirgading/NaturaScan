package com.farez.naturascan.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.R
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.databinding.ActivityMainBinding
import com.farez.naturascan.ui.login.LoginActivity
import com.farez.naturascan.ui.main.fragment.home.HomeFragment
import com.farez.naturascan.ui.main.fragment.saved.SavedPlantsFragment
import com.farez.naturascan.ui.scan.ScanActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var vmFactory: MainVMFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        setupViewModel()
        //nanti hapus
        setupActivity()
        //UNCOMMMENT NANTI
        //checkAuth()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        finishAffinity()
    }

    private fun checkAuth() {
        viewModel.getAuth().observe(this) {
            if (it) {
                setupActivity()
            }
            else {
                Toast.makeText(this, "Error: Token is null",Toast.LENGTH_SHORT ).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun setupActivity() {
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.scan -> {
                    startActivity(
                        Intent(this@MainActivity, ScanActivity::class.java)
                    )
                }
                R.id.saved -> {
                    replaceFragment(SavedPlantsFragment())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.apply {
            setCustomAnimations (R.anim.anim_slide_up, R.anim.anim_slide_down)
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
    fun setupViewModel() {
        vmFactory = MainVMFactory(UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this@MainActivity, vmFactory)[MainViewModel::class.java]
    }
}