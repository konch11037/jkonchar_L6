package com.csci448.konchar.jkonchar_l6.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.I_ScreenSpec
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.maps.android.compose.CameraPositionState

@Composable
fun GeoLocatrNavHost(
    navController: NavController,
    viewModel: I_GeoLocatrViewModel,
    snackbarHostState: SnackbarHostState,
    cameraPositionState: CameraPositionState,
    scaffoldState: ScaffoldState
){
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
                    backStackEntry,
                    snackbarHostState,
                    cameraPositionState,
                    scaffoldState
                    )
            }
        }
    }
}
