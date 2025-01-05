package com.example.androidcookiesfrom1to4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            GymsScreen()
//            GymsDetailsScreen()
            GymsAroundApp()

        }
    }
}

@Composable
private fun GymsAroundApp() {
//    define all screen
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            GymsScreen { id ->
                navController.navigate(route = "gyms/$id")
            }
        }
        composable(route = "gyms/{gym_id}", arguments = listOf(
            navArgument("gym_id") {
                type = NavType.IntType
            }
        )) {
            GymsDetailsScreen()
        }

    }
}
