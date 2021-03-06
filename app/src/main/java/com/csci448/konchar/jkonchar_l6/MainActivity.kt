package com.csci448.konchar.jkonchar_l6

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.ui.navigation.GeoLocatrNavHost
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.AboutScreenSpec
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.HistoryScreenSpec
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.LocationScreenSpec
import com.csci448.konchar.jkonchar_l6.ui.navigation.specs.SettingsScreenSpec
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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                        // create a camera to smoothly move the map view
                        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
//                        Toast.makeText(this@MainActivity, "You were here: " + viewModel.tempy.date + "Temp: "
//                                + viewModel.tempy.temperature + " (" + viewModel.tempy.weather +")", Toast.LENGTH_SHORT).show()
                        // move the camera
                        cameraPositionState.animate(cameraUpdate)
                    }
                }

                val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
                    if(checkRoute(navController = navController)) {
                        FloatingActionButton(
                            onClick = {
                                locationUtility.checkPermissionAndGetLocation(this)
                            },

                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                        }
                    }
                },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState, snackbar =
                        {
                                SnackbarInfo(viewModel = viewModel,
                                    paT = viewModel.tempy)

                        }


                        )

                    },

                    floatingActionButtonPosition = FabPosition.End,

                    topBar = {
                        // TopAppBar Composable
                        TopAppBar(
                            // Provide Title
                            title = {
                                Text(
                                    text = stringResource(id = R.string.app_name),
                                    color = Color.White
                                )
                            },

                            navigationIcon = {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    modifier = Modifier.clickable(onClick = {
                                        coroutineScope.launch {
                                            
                                            scaffoldState.drawerState.open()
                                        }
                                    }),
                                    tint = Color.White
                                )
                            }

                        )
                    },
                    drawerContent = {
                        Column(Modifier.fillMaxSize()) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(LocationScreenSpec.navigateTo())
                                        coroutineScope.launch { scaffoldState.drawerState.close() }
                                    },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_map_24),
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(16.dp))
                                Text("Map")
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(HistoryScreenSpec.navigateTo())
                                        coroutineScope.launch { scaffoldState.drawerState.close() }

                                    },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_history_24),
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(16.dp))
                                Text("History")
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(SettingsScreenSpec.navigateTo())
                                        coroutineScope.launch { scaffoldState.drawerState.close() }
                                    },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(16.dp))
                                Text("Settings")
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(AboutScreenSpec.navigateTo())
                                        coroutineScope.launch { scaffoldState.drawerState.close() }
                                    },
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_info_24),
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(16.dp))
                                Text("About")
                            }
                        }

                    },

                    content = {
                        GeoLocatrNavHost(
                            navController = navController,
                            viewModel = viewModel,
                            snackbarHostState,
                            cameraPositionState,
                            scaffoldState
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

    @Composable
    fun checkRoute(navController: NavHostController): Boolean {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val x = navBackStackEntry?.destination?.route
        return x == "LocationScreen"

    }

    @Composable
fun SnackbarInfo(viewModel: I_GeoLocatrViewModel, paT: PositionAndTime) {
    Column {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("E. LLL d, yyyy hh:mm a")
        val formatted = current.format(formatter)
        val stringy = "You were here: " + paT.date
            Snackbar(
                action = {
                    Button(onClick = {viewModel.deletePositionAndTime(paT.id)}) {
                        Text("DELETE")
                    }
                },
                //modifier = Modifier.padding(8.dp)

            ) {
                Column() {
                Text(fontSize = 12.sp, text = stringy)
                if (paT.temperature != "loading")
                    Text(fontSize = 12.sp, text = "Temp: " + paT.temperature + " (" + paT.weather +")")
                }
            }
    }
}

}