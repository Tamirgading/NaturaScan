package com.farez.naturascan.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.farez.naturascan.data.local.model.Article
import com.farez.naturascan.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

    }

    fun setupView() {
        supportActionBar?.hide()
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        binding.apply {
            val article = intent.getParcelableExtra<Article>("article")
            titleTextView.text = article?.title
            descriptionTextView.text = article?.content
            Glide.with(this@DetailActivity)
                .load(article?.pictureUrl)
                .into(articleImage)
        }
    }
}