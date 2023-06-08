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
import com.farez.naturascan.ui.main.fragment.account.AccountFragment
import com.farez.naturascan.ui.main.fragment.home.HomeFragment
import com.farez.naturascan.ui.main.fragment.saved.SavedPlantsFragment
import com.farez.naturascan.ui.main.fragment.scan.ScanActivity

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
}