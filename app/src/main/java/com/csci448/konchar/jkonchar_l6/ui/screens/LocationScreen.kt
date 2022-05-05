package com.csci448.konchar.jkonchar_l6

import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsActions.Dismiss
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.database.PositionAndTimeRepository
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun LocationScreen(
    locationState: State<Location?>,
    addressState: State<String?>,
    onGetLocation: () -> Unit,
    onMapClick: (PositionAndTime) -> Boolean,
    cameraPositionState: CameraPositionState,
    positionAndTimesStateList: State<List<PositionAndTime>>,
    snackbarHostState: SnackbarHostState,
    scaffoldState: ScaffoldState,
    viewModel: I_GeoLocatrViewModel,
) {
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier
           .padding(16.dp)
           .fillMaxSize()
   ) {
       Text(text = stringResource(id = R.string.latandlong))
       Text(text = "${locationState.value?.latitude ?: ""},${locationState.value?.longitude ?: ""}")

       val allLocationsList : MutableList<LatLng> =remember{ mutableListOf()}
//       positionAndTimesStateList.value.let{
//           it.forEach{
//               allLocationsList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
//           }
//       }

       if (allLocationsList.isEmpty() && locationState.value != null)
       allLocationsList.add(LatLng(locationState.value!!.latitude, locationState.value!!.longitude))

       val coroutine = rememberCoroutineScope()
       GoogleMap(
           modifier = Modifier.weight(1f),
           cameraPositionState = cameraPositionState,
           uiSettings = MapUiSettings(zoomControlsEnabled = false),
           onMapClick = {
               val paT : PositionAndTime = PositionAndTime(longitude = it.longitude.toFloat(),
                   latitude = it.latitude.toFloat(),
                   "", "", Date(Date().date + Date().time))
               if (!onMapClick(paT)){
                   allLocationsList.add(it)
               }
           }
       ) {

           allLocationsList.forEach{paT->
               Marker(
                   position = paT,
                   title = "${paT.latitude ?: ""}, ${paT.longitude ?: ""}",
                   onClick = {
                       onGetLocation()
                       coroutine.launch {
                           val snackbarResult = snackbarHostState.showSnackbar("")
                           when (snackbarResult) {
                               SnackbarResult.ActionPerformed -> {
                                    allLocationsList.remove(paT)
                               }
                               SnackbarResult.Dismissed -> {
                                   /* dismissed, no action needed */
                               }
                           }
                       }
                       true
                   }
               )
           }

           positionAndTimesStateList.value.forEach{paT->
               Marker(
                   position = LatLng(paT.latitude.toDouble(), paT.longitude.toDouble()),
                   title = "${paT.latitude ?: ""}, ${paT.longitude ?: ""}",
                   onClick = {
                       onGetLocation()
                       coroutine.launch {
                           val snackbarResult = snackbarHostState.showSnackbar("")
                           when (snackbarResult) {
                               SnackbarResult.ActionPerformed -> {
                                   viewModel.testDelete(paT)
                               }
                               SnackbarResult.Dismissed -> {
                                   /* dismissed, no action needed */
                               }
                           }

                       }
                       true
                   }
               )
           }


       }
   }
}


