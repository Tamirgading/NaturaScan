package com.farez.naturascan.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.farez.naturascan.data.local.preferences.UserPreferences

class MainViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    fun getAuth() = userPreferences.getAuth().asLiveData()
}

class MainVMFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userPreferences) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}