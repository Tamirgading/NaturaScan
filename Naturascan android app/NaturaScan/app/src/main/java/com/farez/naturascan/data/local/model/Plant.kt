package com.farez.naturascan.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Plant(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id : Int = 0,
    @ColumnInfo("name")
    val name : String,
    @ColumnInfo("latinName")
    val latinName : String,
    @ColumnInfo("photoUri")
    val photoUri : String? = null,
    @ColumnInfo("isEdible")
    val isEdible : Boolean
) : Parcelable

object SamplePlantList {
    val sampleList = listOf(
        Plant(
            1,
            "Plant x",
            "Plantus exus",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-49.jpg",
            isEdible = true
        ),
        Plant(
            2,
            "Plant y",
            "Plantus Yeehaw",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-49.jpg",
            isEdible = false
        ),
        Plant(
            3,
            "Plant a",
            "Plantus aces",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-56.jpg",
            isEdible = true
        ),
        Plant(
            4,
            "Plant b",
            "Plantus becus",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            isEdible = false
        ),
        Plant(
            5,
            "Plant c",
            "Plantus ceces",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            true
        ),
        Plant(
            6,
            "Plant d",
            "Plantus digidaw",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-28-28.jpg",
            isEdible = false
        ),
        Plant(
            7,
            "Plant e",
            "Plantus eeck",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            isEdible = true
        ),
        Plant(
            8,
            "Plant f",
            "Plantus fakus",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            isEdible = false
        ),
        Plant(
            9,
            "Plant g",
            "Plantus gege",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            isEdible = true
        ),
        Plant(
            10,
            "Plant h",
            "Plantus Homagah",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
            isEdible = false
        ),
    )
}
