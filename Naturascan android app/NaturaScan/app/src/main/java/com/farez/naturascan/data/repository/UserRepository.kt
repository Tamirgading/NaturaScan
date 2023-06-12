package com.farez.naturascan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farez.naturascan.api.ApiService
import com.farez.naturascan.data.Status
import com.farez.naturascan.data.remote.response.LoginResponse
import com.farez.naturascan.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    val loginResult = MutableLiveData<Status<LoginResponse>>()
    val registerResult = MutableLiveData<Status<RegisterResponse>>()

    fun login(email: String, password: String): LiveData<Status<LoginResponse>> {
        loginResult.value = Status.Loading
        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        loginResult.value = Status.Success(result)

                    } else {
                        loginResult.value = Status.Error(NULL_RESPONSE)
                    }
                } else {
                    loginResult.value = Status.Error(NO_RESPONSE)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Status.Error(FAILURE)
            }
        })
        return loginResult
    }

    fun register(email: String, password: String): LiveData<Status<RegisterResponse>> {
        registerResult.value = Status.Loading
        apiService.register(email, password).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        registerResult.value = Status.Success(result)

                    } else {
                        registerResult.value = Status.Error(NULL_RESPONSE)
                    }
                } else {
                    registerResult.value = Status.Error(NO_RESPONSE)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = Status.Error(FAILURE)
            }

        })
        return registerResult
    }

    companion object {
        const val NULL_RESPONSE = "Error: Response body is null"
        const val NO_RESPONSE = "Error: Response failed"
        const val FAILURE = "Error: Failed"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService) = instance ?: synchronized(this)
        { instance ?: UserRepository(apiService) }.also { instance = it }
    }
}