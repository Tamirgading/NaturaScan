package com.farez.naturascan.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farez.naturascan.data.local.model.Article
import com.farez.naturascan.databinding.RvHomeArticleBinding

class HomeAdapter(private  val listArticle: List<Article>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    inner class HomeViewHolder(var binding : RvHomeArticleBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = RvHomeArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val article = listArticle[position]
        holder.apply {
            binding.apply {
                descriptionTextView.text = article.content
                articleTitleTextView.text = article.title
            }
        }
    }

    override fun getItemCount(): Int = listArticle.size

}