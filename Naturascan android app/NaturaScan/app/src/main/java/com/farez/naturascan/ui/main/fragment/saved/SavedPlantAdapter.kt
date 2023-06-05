package com.farez.naturascan.ui.main.fragment.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farez.naturascan.R
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.databinding.RvSavedPlantsBinding

class SavedPlantAdapter() : RecyclerView.Adapter<SavedPlantAdapter.SavedPlantViewHolder>() {
    private val listPlantDB = ArrayList<Plant>()
    fun setPlantList(list : List<Plant>) {
        listPlantDB.addAll(list)
    }
    inner class SavedPlantViewHolder(val binding: RvSavedPlantsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPlantViewHolder {
        val binding = RvSavedPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedPlantViewHolder(binding)
    }

    override fun getItemCount(): Int = listPlantDB.size

    override fun onBindViewHolder(holder: SavedPlantViewHolder, position: Int) {
        val plants = listPlantDB[position]
        holder.apply {
            checkEdible(binding, plants.isEdible, plants)
        }
    }
    fun checkEdible (binding: RvSavedPlantsBinding, isEdible : Boolean, plants : Plant) {
        binding.apply {
            Glide.with(imageView.context)
                .load(plants.photoUri)
                .centerCrop()
                .into(imageView)
            if(isEdible) {
                binding.edibilityIcon.setImageResource(R.drawable.edible_icon_svg)
                plantNameTextView.apply {
                    text = plants.name
                    setTextColor(ContextCompat.getColor(this.context, R.color.textGreen))
                }
                latinPlantNameTextView.apply {
                    text = plants.latinName
                    setTextColor(ContextCompat.getColor(this.context, R.color.textLatinGreen))

                }
                edibilityTextView.apply {
                    setTextColor(ContextCompat.getColor(this.context, R.color.textGreen))
                    text = "Tumbuhan bisa dimakan"
                }
            } else {

                binding.edibilityIcon.setImageResource(R.drawable.toxic_icon_svg)
                plantNameTextView.apply {
                    text = plants.name
                    setTextColor(ContextCompat.getColor(this.context, R.color.toxicPurple))
                }
                latinPlantNameTextView.apply {
                    text = plants.latinName
                    setTextColor(ContextCompat.getColor(this.context, R.color.toxicLatinPurple))
                }
                edibilityTextView.apply {
                    setTextColor(ContextCompat.getColor(this.context, R.color.toxicPurple))
                    text = "Tumbuhan beracun"
                }
            }
        }

    }
}