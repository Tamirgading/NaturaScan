package com.farez.naturascan.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.farez.naturascan.data.local.preferences.UserPreferences

class LoginViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    fun checkAuth(): LiveData<Boolean> = userPreferences.getAuth().asLiveData()
}

class LoginVMFactory(private val userPreferences: UserPreferences) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}