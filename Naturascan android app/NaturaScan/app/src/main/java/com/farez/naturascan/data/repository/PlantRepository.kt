package com.farez.naturascan.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farez.naturascan.data.Status
import com.farez.naturascan.data.local.model.Plant
import com.farez.naturascan.data.local.model.SamplePlantList
import com.farez.naturascan.data.remote.emptyresponses
import java.io.File

class PlantRepository {
    val sendPlantResult = MutableLiveData<Status<emptyresponses>>()
    val getPlantResult = MutableLiveData<Status<emptyresponses>>()
    val plantList = MutableLiveData<List<Plant>>()
    fun getPlantList() : LiveData<List<Plant>> {
        //NANTI GANTI
        plantList.value = SamplePlantList.sampleList
        return plantList
    }

    fun sendPlant(pict : File, ) : LiveData<Status<emptyresponses>> {
        sendPlantResult.value = Status.Loading
        // ADD POST PLAN TO API
        return sendPlantResult
    }

    fun getPlant(latinName : String, name : String, isEdible : Boolean, ) : LiveData<Status<emptyresponses>> {
        getPlantResult.value = Status.Loading
        // ADD GET PLAN FROM API
        return getPlantResult
    }

    companion object {
        @Volatile
        private var instance : PlantRepository? = null
        fun getInstance(
            //ADD API SERVICE
        ) = instance ?: synchronized(this) {
            instance ?: PlantRepository(
                //ADD API SERVICE
            )
        }.also { instance = it }
    }
}