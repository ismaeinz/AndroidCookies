package com.example.androidcookiesfrom1to4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class GymsViewModel(
    private val stateHandle: SavedStateHandle
) : ViewModel(

) {
    var state by mutableStateOf(restoredSelectedGyms())
    private fun getGyms() = listOfGyms

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

    private fun restoredSelectedGyms(): List<Gym> {
        val gyms = getGyms()
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIds ->
            savedIds.forEach { gymId ->
                gyms.find { it.id == gymId }?.isFavourite = true
            }
        }
        return gyms
    }

    companion object {
        const val FAV_IDS = "favouriteGymsIDs"

    }
}