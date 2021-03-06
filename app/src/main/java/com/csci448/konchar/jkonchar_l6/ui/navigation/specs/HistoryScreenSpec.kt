package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import android.annotation.SuppressLint
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.ui.screens.HistoryScreen
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.maps.android.compose.CameraPositionState

object HistoryScreenSpec: I_ScreenSpec{
    override val route: String
        get() = "HistoryScreen"
    override val arguments: List<NamedNavArgument>
        get() = TODO("Not yet implemented")

    @SuppressLint("UnrememberedMutableState")
    @Composable
    override fun Content(
        viewModel: I_GeoLocatrViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry,
        snackBarHostState: SnackbarHostState,
        cameraPositionState: CameraPositionState,
        scaffoldState: ScaffoldState
    ) {
       HistoryScreen(positionAndTimesStateList = viewModel.getPositionAndTimes().observeAsState(mutableStateListOf()))
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(vararg args: String?): String {
        return route
    }
}