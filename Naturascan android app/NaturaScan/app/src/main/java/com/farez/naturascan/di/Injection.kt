package com.farez.naturascan.di

import android.app.Application
import com.farez.naturascan.api.ApiConfig
import com.farez.naturascan.data.repository.PlantRepository
import com.farez.naturascan.data.repository.UserRepository

object Injection {
    fun providePlantRepository(application: Application): PlantRepository {
        return PlantRepository(application)
    }

    fun provideUserRepository(): UserRepository {
        return UserRepository(ApiConfig.getApiService())
    }
}