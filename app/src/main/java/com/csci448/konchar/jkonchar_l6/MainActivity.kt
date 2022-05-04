package com.csci448.konchar.jkonchar_l6

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.csci448.konchar.jkonchar_l6.ui.theme.Jkonchar_L6Theme
import com.csci448.konchar.jkonchar_l6.uitl.LocationUtility
import com.csci448.konchar.jkonchar_l6.viewmodels.GeoLocatrViewModel
import com.csci448.konchar.jkonchar_l6.viewmodels.GeoLocatrViewModelFactory
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    lateinit var locationUtility: LocationUtility
    val LOG_TAG = "L6_MainActivity"
    private lateinit var viewModel: GeoLocatrViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = GeoLocatrViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]
        locationUtility = LocationUtility(this, viewModel)
        setContent {
            MainActivityContent(viewModel = viewModel)
            }
        }

    @Composable
    private fun MainActivityContent(viewModel: I_GeoLocatrViewModel) {
        Jkonchar_L6Theme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                val locationState =
                    viewModel.currentLocationLiveData.observeAsState()
                val addressState =
                    viewModel.currentAddressLiveData.observeAsState("")
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
                }

                // add the padding
                val padding = LocalContext.current
                    .resources
                    .getDimensionPixelSize(R.dimen.map_inset_padding)

                LaunchedEffect(locationState.value) {
                    // update the current address if the location moves
                    locationUtility.getAddressForCurrentLocation(this@MainActivity, viewModel)

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

                val scaffoldState = rememberScaffoldState()
                Scaffold(floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            locationUtility.checkPermissionAndGetLocation(this)
                        },

                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                    }
                },

                    floatingActionButtonPosition = FabPosition.End,

                    topBar = {
                        TopAppBar() {

                        }

                    },

                    content = {
                        LocationScreen(
                            locationState = locationState,
                            addressState = addressState,
                            onGetLocation = {
                                locationUtility.checkPermissionAndGetLocation(this)
                            },
                            cameraPositionState = cameraPositionState,
                            positionAndTimesStateList = positionAndTimesStateList
                        )
                    }
                )

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