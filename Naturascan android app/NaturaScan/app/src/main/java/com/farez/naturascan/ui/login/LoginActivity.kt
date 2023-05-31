package com.farez.naturascan.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.farez.naturascan.R
import com.farez.naturascan.ui.login.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.frameLayout, LoginFragment(), LoginFragment::class.java.simpleName)
            commit()
        }
        supportActionBar?.hide()
    }
}