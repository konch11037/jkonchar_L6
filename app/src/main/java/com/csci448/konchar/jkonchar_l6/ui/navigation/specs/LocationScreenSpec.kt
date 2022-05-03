package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.LocationScreen
import com.csci448.konchar.jkonchar_l6.viewmodels.GeoLocatrViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

object LocationScreenSpec: I_ScreenSpec {
    
    override val route: String
        get() ="LocationScreen"
    override val arguments: List<NamedNavArgument>
        get() = TODO("Not yet implemented")

    @Composable
    override fun Content(
        viewModel: GeoLocatrViewModel,
        navController: NavHostController,
        backStackEntry: NavBackStackEntry
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0,0.0), 0f)
        }
       LocationScreen(
           locationState = viewModel.currentLocationLiveData.observeAsState(),
           addressState = viewModel.currentAddressLiveData.observeAsState(),
           onGetLocation = { /*TODO*/ },
           cameraPositionState = cameraPositionState
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