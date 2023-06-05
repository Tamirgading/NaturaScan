package com.farez.naturascan.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farez.naturascan.data.local.model.Plant

@Database (entities = [Plant::class], version = 1)
abstract class PlantRoomDB : RoomDatabase() {

    abstract fun plantao() : PlantDAO

    companion object {
        @Volatile
        private var INSTANCE: PlantRoomDB? = null
        @JvmStatic
        fun getDatabase(context: Context): PlantRoomDB {
            if (INSTANCE == null) {
                synchronized(PlantRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        PlantRoomDB::class.java, "plant_database")
                        .build()
                }
            }
            return INSTANCE as PlantRoomDB
        }
    }

}