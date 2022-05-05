package com.csci448.konchar.jkonchar_l6

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter




@Composable
fun LocationScreen(
    locationState: State<Location?>,
    addressState: State<String?>,
    onGetLocation: () -> Unit,
    cameraPositionState: CameraPositionState,
    positionAndTimesStateList: State<List<PositionAndTime>>,
    snackbarHostState: SnackbarHostState,
    scaffoldState: ScaffoldState

) {
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier
           .padding(16.dp)
           .fillMaxSize()
   ) {
       Text(text = stringResource(id = R.string.latandlong))
       Text(text = "${locationState.value?.latitude ?: ""},${locationState.value?.longitude ?: ""}")

       val allLocationsList : MutableList<LatLng> = mutableListOf()
       positionAndTimesStateList.value.let{
           it.forEach{
               allLocationsList.add(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
           }
       }

       if (allLocationsList.isEmpty() && locationState.value != null)
       allLocationsList.add(LatLng(locationState.value!!.latitude, locationState.value!!.longitude))


       val coroutine = rememberCoroutineScope()
       GoogleMap(
           modifier = Modifier.weight(1f),
           cameraPositionState = cameraPositionState,
           uiSettings = MapUiSettings(zoomControlsEnabled = false)
       ) {
           allLocationsList.forEach{
           if (locationState.value != null) {
               Marker(
                   position = it,
                   title = "${locationState.value?.latitude ?: ""}, ${locationState.value?.longitude ?: ""}",
                   onClick = {
                       onGetLocation()
                       coroutine.launch {
                           scaffoldState.snackbarHostState.showSnackbar("test")
                       }
                       //trackShit.value = true
                       false
                   }
               )
           }
           }
       }

   }
}


