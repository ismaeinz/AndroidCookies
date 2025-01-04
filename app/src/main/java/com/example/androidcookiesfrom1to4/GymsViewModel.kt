package com.example.androidcookiesfrom1to4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(
    private val stateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(emptyList<Gym>())
    private var apiServices: GymsApiServices
    private val errorHandler =
        CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }

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
        viewModelScope.launch(errorHandler) {
            val gyms = getGymsFromRemoteDb()
            withContext(Dispatchers.Main) {
                state = gyms.restoredSelectedGyms()
            }


        }

    }

    private suspend fun getGymsFromRemoteDb() = withContext(Dispatchers.IO) {
        apiServices.getGyms()
    }


    fun toggleFavouriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
        storedSelectedGyms(gyms[itemIndex])
        state = gyms
    }

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

