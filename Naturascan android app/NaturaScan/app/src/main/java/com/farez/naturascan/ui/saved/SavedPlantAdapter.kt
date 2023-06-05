package com.farez.naturascan.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farez.naturascan.R
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.databinding.RvSavedPlantsBinding

class SavedPlantAdapter(val listPlant: List<Plant>) : RecyclerView.Adapter<SavedPlantAdapter.SavedPLantViewHolder>() {
    inner class SavedPLantViewHolder(val binding: RvSavedPlantsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPLantViewHolder {
        val binding = RvSavedPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedPLantViewHolder(binding)
    }

    override fun getItemCount(): Int = listPlant.size

    override fun onBindViewHolder(holder: SavedPLantViewHolder, position: Int) {
        val plants = listPlant[position]
        holder.apply {
            checkEdible(binding, plants.isEdible, plants)
        }
    }
    fun checkEdible (binding: RvSavedPlantsBinding, isEdible : Boolean, plants : Plant) {
        binding.apply {
            if(isEdible) {
                binding.edibilityIcon.setImageResource(R.drawable.edible_icon_svg)
                plantNameTextView.apply {
                    text = plants.name
                    setTextColor(resources.getColor(R.color.textGreen))
                }
                latinPlantNameTextView.apply {
                    text = plants.latinName
                    setTextColor(resources.getColor(R.color.textLatinGreen))

                }
                edibilityTextView.apply {
                    setTextColor(resources.getColor(R.color.textGreen))
                    text = "Tumbuhan bisa dimakan"
                }
            } else {

                binding.edibilityIcon.setImageResource(R.drawable.toxic_icon_svg)
                plantNameTextView.apply {
                    text = plants.name
                    setTextColor(resources.getColor(R.color.toxicPurple))
                }
                latinPlantNameTextView.apply {
                    text = plants.latinName
                    setTextColor(resources.getColor(R.color.toxicLatinPurple))
                }
                edibilityTextView.apply {
                    setTextColor(resources.getColor(R.color.toxicPurple))
                    text = "Tumbuhan beracun"
                }
            }
        }

    }
}