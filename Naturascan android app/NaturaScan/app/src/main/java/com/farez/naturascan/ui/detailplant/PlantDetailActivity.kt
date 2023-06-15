package com.farez.naturascan.ui.detailplant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.databinding.ActivityPlantDetailBinding

class PlantDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlantDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val plant = intent.getParcelableExtra<Plant>("PLANT")!!
        Glide.with(this@PlantDetailActivity)
            .load(plant.photoUri)
            .fitCenter()
            .into(binding.imageView4)
        binding.plantNameTextVIew.text = plant.name
        binding.plantDescriptionTextView.text = plant.desc
    }
}