package com.farez.naturascan.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.repository.PlantRepository

class ScanViewModel(private val plantRepository: PlantRepository) : ViewModel() {
    fun insert(plant: Plant) = plantRepository.insertPlant(plant)
}

class ScanVMFactory(private val plantRepository: PlantRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(plantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}