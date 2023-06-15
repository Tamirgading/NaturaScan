package com.farez.naturascan.ui.main.fragment.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.repository.PlantRepository

class SavedPlantViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    fun getPlantLIst() = plantRepository.getPlantList()
    fun deletePlant(plant: Plant) = plantRepository.deletePlant(plant)

}

class SavedPlantVMFactory(private val plantRepository: PlantRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedPlantViewModel::class.java)) {
            return SavedPlantViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}