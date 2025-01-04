package com.example.androidcookiesfrom1to4

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel : ViewModel() {
    val state = mutableStateOf<Gym?>(null)

    //    implemntion for interface
    private var apiServices: GymsApiServices

    //    انشياليست اوبجكت
    init {
        val retroFit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl(
                "https://cairo-gyms-f29c8-default-rtdb.firebaseio.com/"
            ).build()
        apiServices = retroFit.create(GymsApiServices::class.java)
        getGym(2)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            val gym = getGymFromRemoteDB(id)
            state.value = gym
        }

    }

    private suspend fun getGymFromRemoteDB(id: Int) =
        withContext(Dispatchers.IO) {
            apiServices.getGym(id).values.first()
        }

}