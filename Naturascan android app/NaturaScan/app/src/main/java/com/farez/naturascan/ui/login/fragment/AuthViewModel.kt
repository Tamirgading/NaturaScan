package com.farez.naturascan.ui.login.fragment

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.farez.naturascan.data.local.preferences.UserPreferences
import com.farez.naturascan.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun register(email: String, password: String) = userRepository.register(email, password)

    fun saveLoginInfo(token: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.saveLoginInfo(token, email)
        }
    }

    fun setAuth(auth: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.setAuth(auth)
        }
    }

    fun getAuth(): LiveData<Boolean> {
        return userPreferences.getAuth().asLiveData()
    }
}

class AuthVMFactory(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository, userPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}