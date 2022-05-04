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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


//@Preview(showBackground = true)
//@Composable
//fun PreviewLocationScreen() {
//    val locationState = rememberSaveable { mutableStateOf<Location?>(null) }
//    val addressState = rememberSaveable { mutableStateOf("") }
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
//    }
//
//    LocationScreen(
//        locationState = locationState,
//        addressState = addressState,
//        onGetLocation = {
//            locationState.value = Location("").apply {
//                latitude = 1.35
//                longitude = 103.87
//            }
//        addressState.value = "Singapore"
//        },
//        cameraPositionState = cameraPositionState,
//        positionAndTimesStateList = positionAndTimesStateList
//    )
//}

@Composable
fun LocationScreen(
    locationState: State<Location?>,
    addressState: State<String?>,
    onGetLocation: () -> Unit,
    cameraPositionState: CameraPositionState,
    positionAndTimesStateList: State<List<PositionAndTime>>
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



       var trackShit = remember { mutableStateOf(false)}
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
                       trackShit.value = true
                       false
                   }
               )
           }
           }
       }

       if (trackShit.value) {
            SnackbarDemo(trackShit = trackShit)

       }
   }
}

@Composable
fun SnackbarDemo(trackShit: MutableState<Boolean>) {
    Column {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("E. LLL d, yyyy hh:mm a")
        val formatted = current.format(formatter)
        val stringy = "You were here: " + formatted
            Snackbar(
                action = {
                    Button(onClick = {trackShit.value = false}) {
                        Text("DELETE")
                    }
                },
                //modifier = Modifier.padding(8.dp)

            ) { Text(fontSize = 12.sp, text = stringy.toString()) }
    }
}