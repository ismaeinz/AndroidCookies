package com.example.androidcookiesfrom1to4

import com.google.gson.annotations.SerializedName

//val listOfGyms = listOf<Gym>(
//    Gym(0, "nameOfGym", "PlaceOfGym"),
//
//    )

data class Gym(
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    var isFavourite: Boolean = false,
)
