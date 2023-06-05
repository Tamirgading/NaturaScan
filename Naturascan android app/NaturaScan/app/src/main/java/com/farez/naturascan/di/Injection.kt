package com.farez.naturascan.di

import android.app.Application
import android.content.Context
import com.farez.naturascan.api.ApiConfig
import com.farez.naturascan.data.local.database.PlantRoomDB
import com.farez.naturascan.data.repository.PlantRepository

object Injection {
    fun provideRepository(context: Context, application: Application) : PlantRepository {
        val database = PlantRoomDB.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return PlantRepository(application, database, apiService)
    }
}