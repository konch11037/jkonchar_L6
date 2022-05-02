package com.csci448.konchar.jkonchar_l6

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Preview(showBackground = true)
@Composable
fun PreviewLocationScreen() {
    val locationState = rememberSaveable { mutableStateOf<Location?>(null) }
    val addressState = rememberSaveable { mutableStateOf("") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 0f)
    }

    LocationScreen(
        locationState = locationState,
        addressState = addressState,
        onGetLocation = {
            locationState.value = Location("").apply {
                latitude = 1.35
                longitude = 103.87
            }
        addressState.value = "Singapore"
        },
        cameraPositionState = cameraPositionState
    )
}

@Composable
fun LocationScreen(locationState: State<Location?>,
                    addressState: State<String?>,
                    onGetLocation: () -> Unit,
                    cameraPositionState: CameraPositionState) {
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = Modifier
           .padding(16.dp)
           .fillMaxSize()
   ) {
       Text(text = stringResource(id = R.string.latandlong))
       val lat = locationState.value?.latitude ?: "5"
       Text(text = "${locationState.value?.latitude ?: ""},${locationState.value?.longitude ?: ""}")

       val locationPosition = locationState.value?.let {
           LatLng(it.latitude, it.longitude)
       } ?: LatLng(0.0,0.0)

       GoogleMap(
           modifier = Modifier.weight(1f),
           cameraPositionState = cameraPositionState
       ) {
           if (locationState.value != null) {
               Marker(
                   position = locationPosition,
                   title = addressState.value,
                   snippet ="${locationState.value?.latitude ?: ""}, ${locationState.value?.longitude ?: ""}"
               )
           }
       }

   }
}