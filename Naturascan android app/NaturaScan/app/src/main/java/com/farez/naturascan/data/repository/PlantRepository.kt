package com.farez.naturascan.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farez.naturascan.api.ApiService
import com.farez.naturascan.data.Status
import com.farez.naturascan.data.local.database.PlantDAO
import com.farez.naturascan.data.local.database.PlantRoomDB
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.remote.emptyresponses
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PlantRepository(application: Application, database : PlantRoomDB, apiService: ApiService) {
    val uploadPlantResult = MutableLiveData<Status<emptyresponses>>()
    val getPlantResult = MutableLiveData<Status<emptyresponses>>()
    private val plantDAO : PlantDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PlantRoomDB.getDatabase(application)
        plantDAO = db.plantao()
    }

    fun getPlantList() : LiveData<List<Plant>> = plantDAO.getAllPlants()

    fun insertPlant(plant: Plant) { executorService.execute{plantDAO.insert(plant)} }

    fun deletePlant(plant: Plant){ executorService.execute { plantDAO.delete(plant)} }


    fun uploadPlant(pict : File, ) : LiveData<Status<emptyresponses>> {
        uploadPlantResult.value = Status.Loading
        val imgToMedia = "image/jpeg".toMediaTypeOrNull()
        val imgMultiPart: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", pict.name, pict.asRequestBody(imgToMedia))
        // ADD POST PLAN TO API
        return uploadPlantResult
    }

    fun getPlant(name : String, latinName : String, isEdible : Boolean, ) : LiveData<Status<emptyresponses>> {
        getPlantResult.value = Status.Loading
        // ADD GET PLAN FROM API
        return getPlantResult
    }

    companion object {
        @Volatile
        private var instance : PlantRepository? = null
        fun getInstance(application: Application, database: PlantRoomDB, apiService: ApiService) {
            instance ?: synchronized(this) { instance ?: PlantRepository(application, database, apiService) }.also { instance = it }
        }
    }
}