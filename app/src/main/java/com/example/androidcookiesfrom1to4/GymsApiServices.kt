package com.example.androidcookiesfrom1to4

import retrofit2.http.GET

interface GymsApiServices {

    @GET("gyms.json")
    suspend fun getGyms(): List<Gym>
}

