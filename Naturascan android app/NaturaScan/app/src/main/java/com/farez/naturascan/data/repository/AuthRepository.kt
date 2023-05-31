package com.farez.naturascan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farez.naturascan.data.Status
import com.farez.naturascan.data.remote.emptyresponses

class AuthRepository {
    val loginResult = MutableLiveData<Status<emptyresponses>>()
    val registerResult = MutableLiveData<Status<emptyresponses>>()

    fun login(email : String, password: String) : LiveData<Status<emptyresponses>> {
        loginResult.value = Status.Loading
        //login API
        return loginResult
    }
    fun register(email : String, password : String) : LiveData<Status<emptyresponses>> {
        registerResult.value = Status.Loading
        //REGISTER API
        return registerResult
    }

    companion object {
        @Volatile
        private var instance : AuthRepository? = null
        fun getInstance(
            //ADD API SERVICE
        ) = instance ?: synchronized(this) {
            instance ?: AuthRepository(
                //ADD API SERVICE
            )
        }.also { instance = it }
    }
}