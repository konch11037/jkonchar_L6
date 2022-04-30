package com.csci448.konchar.jkonchar_l6

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.csci448.konchar.jkonchar_l6.ui.theme.Jkonchar_L6Theme
import com.csci448.konchar.jkonchar_l6.LocationUtility
import com.csci448.konchar.jkonchar_l6.R.dimen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    lateinit var locationUtility: LocationUtility
    val LOG_TAG = "L6_MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationUtility = LocationUtility(this)
        setContent {
            Jkonchar_L6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val locationState =
                        locationUtility.viewModel.currentLocationLiveData.observeAsState()
                    val addressState =
                        locationUtility.viewModel.currentAddressLiveData.observeAsState("")
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(LatLng(0.0,0.0), 0f)
                    }

                    // add the padding
                    val padding = LocalContext.current
                        .resources
                        .getDimensionPixelSize(R.dimen.map_inset_padding)

                    LaunchedEffect(locationState.value) {
                        // update the current address if the location moves
                        locationUtility.getAddressForCurrentLocation(this@MainActivity)

                        // update the camera for the map whenever location moves
                        val locationPosition = locationState.value?.let {
                            LatLng(it.latitude, it.longitude)
                        }
                        if (locationPosition != null) {
                            // Include all points that should be within the bounds of the zoom
                            // convex hull
                            val bounds = LatLngBounds.Builder()
                                .include(locationPosition)
                                .build()
                            // create a camera to smoothjly move the map view
                            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                            // move the camera
                            cameraPositionState.animate(cameraUpdate)
                        }
                    }

                    LocationScreen(
                        locationState = locationState,
                        addressState = addressState,
                        onGetLocation = {
                            locationUtility.checkPermissionAndGetLocation(this)
                        },
                        cameraPositionState = cameraPositionState
                    )
                }
            }
        }
    }

    override fun onStop() {
        Log.d(LOG_TAG, "onStop() called")
        super.onStop()
        locationUtility.removeLocationRequest()
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Jkonchar_L6Theme {
            Greeting("Android")
        }
    }

}