package com.example.androidcookiesfrom1to4

import retrofit2.Call
import retrofit2.http.GET

interface GymsApiServices {

    @GET("gyms.json")
    fun getGyms(): Call<List<Gym>>
}

