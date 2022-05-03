package com.csci448.konchar.jkonchar_l6.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.I_ScreenSpec
import com.csci448.konchar.jkonchar_l6.viewmodels.GeoLocatrViewModel

@Composable
fun GeoLocatrNavHost(navController: NavController, viewModel: GeoLocatrViewModel){
    NavHost(
        navController = navController as NavHostController,
        startDestination = I_ScreenSpec.startDestination) {
        I_ScreenSpec.allScreens.forEach { (route ,screen) ->
            composable(
                route = route!!,
            ) { backStackEntry ->
                screen?.Content(
                    viewModel,
                    navController,
                    backStackEntry)
            }
        }
    }
}
