package com.farez.naturascan.ui.main.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.farez.naturascan.data.local.preferences.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val userPreferences: UserPreferences) : ViewModel() {
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.delLoginInfo()
            userPreferences.setAuth(false)
        }
    }
    fun getEmail() = userPreferences.getEmail().asLiveData()
}

class HomeVMFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}