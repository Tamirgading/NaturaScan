package com.farez.naturascan.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.farez.naturascan.data.local.database.PlantDAO
import com.farez.naturascan.data.local.database.PlantRoomDB
import com.farez.naturascan.data.local.model.Plant
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PlantRepository(application: Application) {
    private val plantDAO: PlantDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PlantRoomDB.getDatabase(application)
        plantDAO = db.plantao()
    }

    fun getPlantList(): LiveData<List<Plant>> = plantDAO.getAllPlants()

    fun insertPlant(plant: Plant) {
        executorService.execute { plantDAO.insert(plant) }
    }

    fun deletePlant(plant: Plant) {
        executorService.execute { plantDAO.delete(plant) }
    }


}