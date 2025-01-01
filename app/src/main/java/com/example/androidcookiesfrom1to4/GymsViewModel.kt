package com.example.androidcookiesfrom1to4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle: SavedStateHandle,
) : ViewModel(

) {
    //    for network request
    var state by mutableStateOf(emptyList<Gym>())
    private var apiServices: GymsApiServices
    private lateinit var gymsCall: Call<List<Gym>>

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .baseUrl(
                "https://cairo-gyms-f29c8-default-rtdb.firebaseio.com/"
            ).build()
        apiServices = retrofit.create(GymsApiServices::class.java)
        getGyms()
    }

    private fun getGyms() {
        gymsCall = apiServices.getGyms()
        gymsCall.enqueue(object : Callback<List<Gym>> {
            override fun onResponse(
                call: Call<List<Gym>>,
                response:
                Response<List<Gym>>,
            ) {
                response.body()?.let {
                    state = it.restoredSelectedGyms()
                }
            }

            override fun onFailure(call: Call<List<Gym>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        gymsCall.cancel()
    }


    fun toggleFavouriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
        storedSelectedGyms(gyms[itemIndex])
        state = gyms
    }

    //
    private fun storedSelectedGyms(gym: Gym) {
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS)
            .orEmpty()
            .toMutableList()
        if (gym.isFavourite) savedHandleList.add(gym.id)
        else savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList

    }

    private fun List<Gym>.restoredSelectedGyms(): List<Gym> {
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                this.find { it.id == gymId }?.isFavourite = true
            }
        }
        return this
    }

    companion object {
        const val FAV_IDS = "favouriteGymsIDs"

    }
}

