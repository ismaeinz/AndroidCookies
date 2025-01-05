package com.example.androidcookiesfrom1to4

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymsScreen(onItemClick: (Int) -> Unit) {
    val vmGyms: GymsViewModel = viewModel()

    LazyColumn {
        items(vmGyms.state) { gym ->
            GymItem(gym = gym,
                onFavouriteClick = {
                    vmGyms.toggleFavouriteState(it)
                }, onItemClick = { id -> onItemClick(id) })
        }

    }
}

@Composable
fun GymItem(gym: Gym, onFavouriteClick: (Int) -> Unit, onItemClick: (Int) -> Unit) {
    val icon = if (gym.isFavourite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onItemClick(gym.id) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DefaultIcon(
                vector = Icons.Filled.Place,
                modifier = Modifier.weight(.1f)
            )
            GymDetails(
                gym,
                Modifier.weight(.7f)
            )
            DefaultIcon(
                icon,
                Modifier
                    .weight(.1f)
            ) {
                onFavouriteClick(gym.id)
            }

        }
    }
}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Gray,
        )
        Text(
            text = gym.place,
            style = MaterialTheme.typography.bodySmall,
            color = Color.DarkGray
        )

    }
}

@Composable
fun DefaultIcon(vector: ImageVector, modifier: Modifier, onClick: () -> Unit = {}) {
    Image(
        imageVector = vector,
        contentDescription = "contentDescription",
        modifier = modifier.clickable { onClick() }
    )
}
