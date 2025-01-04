package com.example.androidcookiesfrom1to4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymsDetailsScreen() {
    val viewModel: GymsDetailsViewModel = viewModel()
    val item = viewModel.state.value
    item?.let {
        Column {
            DefaultIcon(Icons.Filled.Place, modifier = Modifier)
            GymDetails(gym = item, modifier = Modifier.padding(10.dp))
            Text(
                text = if (item.isOpen) "Gyms is Open " else "Gym is Close",
                color = if (item.isOpen) Color.Green else Color.Red
            )
        }
    }


}