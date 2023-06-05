package com.farez.naturascan.ui.main.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.farez.naturascan.data.local.preferences.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel (private val userPreferences: UserPreferences) : ViewModel(){
    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.delToken()
            userPreferences.setAuth(false)
        }
    }
}

class AccountVMFactory (private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}