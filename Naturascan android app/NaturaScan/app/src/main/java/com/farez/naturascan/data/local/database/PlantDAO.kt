package com.farez.naturascan.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.farez.naturascan.data.local.model.Plant

@Dao
interface PlantDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(plant: Plant)

    @Delete
    fun delete(plant: Plant)

    @Query("SELECT * FROM plant ORDER BY name ASC")
    fun getAllPlants(): LiveData<List<Plant>>
}