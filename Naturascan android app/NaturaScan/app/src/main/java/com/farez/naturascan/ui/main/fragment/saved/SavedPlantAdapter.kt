package com.farez.naturascan.ui.main.fragment.saved

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farez.naturascan.R
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.repository.PlantRepository
import com.farez.naturascan.databinding.RvSavedPlantsBinding
import com.farez.naturascan.ui.detailplant.PlantDetailActivity

class SavedPlantAdapter(private val application: Application) :
    RecyclerView.Adapter<SavedPlantAdapter.SavedPlantViewHolder>() {
    private val listPlantDB = ArrayList<Plant>()
    fun setPlantList(list: List<Plant>) {
        listPlantDB.clear()
        listPlantDB.addAll(list)
    }

    inner class SavedPlantViewHolder(val binding: RvSavedPlantsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plants: Plant) {
            binding.apply {
                Glide.with(imageView.context)
                    .load(plants.photoUri)
                    .centerCrop()
                    .into(imageView)
                edibilityIcon.setImageResource(R.drawable.edible_icon_svg)
                plantNameTextView.text = plants.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedPlantViewHolder {
        val binding =
            RvSavedPlantsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedPlantViewHolder(binding)
    }

    override fun getItemCount(): Int = listPlantDB.size

    override fun onBindViewHolder(holder: SavedPlantViewHolder, position: Int) {
        val plant = listPlantDB[position]
        holder.bind(plant)
        holder.apply {
            binding.deleteButton.setOnClickListener {
                PlantRepository(application).deletePlant(plant)
                listPlantDB.remove(plant)
                notifyItemRemoved(position)
            }
            itemView.setOnClickListener {
                val intent = Intent(it.context, PlantDetailActivity::class.java)
                    .putExtra("PLANT", plant)
                it.context.startActivity(intent)
            }
        }
    }
}