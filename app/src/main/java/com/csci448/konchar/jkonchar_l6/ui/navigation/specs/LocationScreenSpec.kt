package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.LocationScreen
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.*

object LocationScreenSpec: I_ScreenSpec {
    
    override val route: String
        get() ="LocationScreen"
    override val arguments: List<NamedNavArgument>
        get() = TODO("Not yet implemented")

    @SuppressLint("UnrememberedMutableState")
    @Composable
    override fun Content(
        viewModel: I_GeoLocatrViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0,0.0), 0f)
        }
        val locationState = viewModel.currentLocationLiveData.observeAsState()

        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(0.0,0.0)

        val pat = PositionAndTime(
            longitude = locationPosition.longitude.toFloat(),
            latitude = locationPosition.latitude.toFloat(),
            "",  //TODO: FIX
            "",
            Date(Date().date + Date().time)
            )


        viewModel.addPositionAndTime(pat)

        val positionAndTimesStateList = viewModel.getPositionAndTimes().observeAsState(
            mutableStateListOf())

       LocationScreen(
           locationState = locationState,
           addressState = viewModel.currentAddressLiveData.observeAsState(),
           onGetLocation = {},
           cameraPositionState = cameraPositionState,
           positionAndTimesStateList = positionAndTimesStateList
       ) 
    }

    @Composable
    override fun TopAppBarActions(navController: NavHostController) {
        TODO("Not yet implemented")
    }

    override fun navigateTo(vararg args: String?): String {
       return route
    }
}