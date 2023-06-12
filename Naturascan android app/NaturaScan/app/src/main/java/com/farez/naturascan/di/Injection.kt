package com.farez.naturascan.di

import android.app.Application
import android.content.Context
import com.farez.naturascan.api.ApiConfig
import com.farez.naturascan.data.repository.PlantRepository
import com.farez.naturascan.data.repository.UserRepository

object Injection {
    fun providePlantRepository(context: Context, application: Application): PlantRepository {
        return PlantRepository(application)
    }

    fun provideUserRepository(): UserRepository {
        return UserRepository(ApiConfig.getApiService())
    }
}