package com.example.androidcookiesfrom1to4

val listOfGyms = listOf<Gym>(
    Gym(0, "nameOfGym", "PlaceOfGym"),

    )

data class Gym(
    val id: Int,
    val name: String,
    val place: String,
    var isFavourite: Boolean = false
)
