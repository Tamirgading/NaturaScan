package com.farez.naturascan.ui.main.fragment.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.data.repository.PlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedPlantViewModel(private val  plantRepository: PlantRepository,) : ViewModel() {
    fun getPlantLIst() = plantRepository.getPlantList()
    fun insertPlant(plant: Plant) = plantRepository.insertPlant(plant)

}

class SavedPlantVMFactory (private val plantRepository: PlantRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedPlantViewModel::class.java)) {
            return SavedPlantViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}