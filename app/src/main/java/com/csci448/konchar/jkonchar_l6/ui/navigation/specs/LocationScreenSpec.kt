package com.csci448.konchar.jkonchar_l6.ui.navigation.specs

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.csci448.konchar.jkonchar_l6.LocationScreen
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import com.csci448.konchar.jkonchar_l6.data.makeApiWeatherRequest
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executors

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
        backStackEntry: NavBackStackEntry,
        snackbarHostState: SnackbarHostState,
        cameraPositionState: CameraPositionState,
        scaffoldState: ScaffoldState
    ) {

        val locationState = viewModel.currentLocationLiveData.observeAsState()

        val locationPosition = locationState.value?.let {
            LatLng(it.latitude, it.longitude)
        } ?: LatLng(0.0,0.0)

        val pat = PositionAndTime(
            longitude = locationPosition.longitude.toFloat(),
            latitude = locationPosition.latitude.toFloat(),
            "loading",
            "loading",
            Date(Date().date + Date().time)
            )

        val save = viewModel.getUserSettings().observeAsState(UserSettings()).value.saveLocation

        val positionAndTimesStateList = viewModel.getPositionAndTimes().observeAsState(
            mutableStateListOf())

        val context = LocalContext.current
        val executor = Executors.newSingleThreadExecutor()
        val coroutineScope = rememberCoroutineScope()
        LocationScreen(
           locationState = locationState,
           addressState = viewModel.currentAddressLiveData.observeAsState(),
           onGetLocation = {

               coroutineScope.launch {
                   withContext(Dispatchers.Main) {
                       viewModel.tempy = pat
                   }
               }
           },
            onMapClick = { positionAndTime: PositionAndTime ->
                Toast.makeText(context, "You were here: " + positionAndTime.date + "Temp: "
                        + positionAndTime.temperature + " (" + positionAndTime.weather +")", Toast.LENGTH_LONG).show()
                if (save) viewModel.addPositionAndTime(positionAndTime)
                save
            },
           cameraPositionState = cameraPositionState,
           positionAndTimesStateList = positionAndTimesStateList,
           snackbarHostState = snackbarHostState,
           scaffoldState
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