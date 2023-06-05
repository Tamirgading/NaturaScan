package com.farez.naturascan.ui.main

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.farez.naturascan.R
import com.farez.naturascan.databinding.ActivityMainBinding
import com.farez.naturascan.ui.account.AccountFragment
import com.farez.naturascan.ui.home.HomeFragment
import com.farez.naturascan.ui.saved.SavedPlantsFragment
import com.farez.naturascan.ui.scan.ScanActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.scan -> {
                    gotoScan()
                }
                R.id.saved -> {
                    replaceFragment(SavedPlantsFragment())
                }
                R.id.account -> {
                    replaceFragment(AccountFragment())
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }

    private fun gotoScan() {
        startActivity(Intent(this@MainActivity, ScanActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drawer_item, menu)
        return super.onCreateOptionsMenu(menu)
    }
}