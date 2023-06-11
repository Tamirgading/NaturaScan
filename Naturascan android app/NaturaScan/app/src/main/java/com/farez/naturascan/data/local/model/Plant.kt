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
    @ColumnInfo("photoUri")
    val photoUri : String? = null,
) : Parcelable

object SamplePlantList {
    val sampleList = listOf(
        Plant(
            1,
            "Plant x",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-49.jpg",
        ),
        Plant(
            2,
            "Plant y",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-49.jpg",
        ),
        Plant(
            3,
            "Plant a",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-48-56.jpg",
        ),
        Plant(
            4,
            "Plant b",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
        Plant(
            5,
            "Plant c",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
        Plant(
            6,
            "Plant d",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-15-28-28.jpg",
        ),
        Plant(
            7,
            "Plant e",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
        Plant(
            8,
            "Plant f",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
        Plant(
            9,
            "Plant g",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
        Plant(
            10,
            "Plant h",
            "file:///storage/emulated/0/Android/media/com.farez.naturascan/NaturaScan/2023-06-05-17-10-04.jpg",
        ),
    )
}
