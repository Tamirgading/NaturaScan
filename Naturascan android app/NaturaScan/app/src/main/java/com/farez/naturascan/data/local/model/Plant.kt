package com.farez.naturascan.data.local.model

import java.io.File

data class Plant(
    val id : Int,
    val name : String,
    val latinName : String,
    val photo : File? = null,
    val isEdible : Boolean
)

object SamplePlantList {
    val sampleList = listOf(
        Plant(
            1,
            "Plant x",
            "Plantus exus",
            null,
            isEdible = true
        ),
        Plant(
            2,
            "Plant y",
            "Plantus Yeehaw",
            null,
            isEdible = false
        ),
        Plant(
            3,
            "Plant a",
            "Plantus aces",
            null,
            isEdible = true
        ),
        Plant(
            4,
            "Plant b",
            "Plantus becus",
            null,
            isEdible = false
        ),
        Plant(
            5,
            "Plant c",
            "Plantus ceces",
            null,
            isEdible = true
        ),
        Plant(
            6,
            "Plant d",
            "Plantus digidaw",
            null,
            isEdible = false
        ),
        Plant(
            7,
            "Plant e",
            "Plantus eeck",
            null,
            isEdible = true
        ),
        Plant(
            8,
            "Plant f",
            "Plantus fakus",
            null,
            isEdible = false
        ),
        Plant(
            9,
            "Plant g",
            "Plantus gege",
            null,
            isEdible = true
        ),
        Plant(
            10,
            "Plant h",
            "Plantus Homagah",
            null,
            isEdible = false
        ),
    )
}
