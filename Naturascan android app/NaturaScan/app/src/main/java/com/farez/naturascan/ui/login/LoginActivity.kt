package com.farez.naturascan.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.R
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.ui.login.fragment.LoginFragment
import com.farez.naturascan.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TOKEN")


class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var vmFactory: LoginVMFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupViewModel()
        checkAuth()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.frameLayout, LoginFragment(), LoginFragment::class.java.simpleName)
            commit()
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        vmFactory = LoginVMFactory(UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]
    }

    private fun checkAuth() {
        viewModel.checkAuth().observe(this) {
            if (it) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }
    }
}