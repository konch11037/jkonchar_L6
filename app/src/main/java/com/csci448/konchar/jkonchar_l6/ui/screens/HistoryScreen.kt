package com.csci448.konchar.jkonchar_l6.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csci448.konchar.jkonchar_l6.R
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.gms.maps.model.LatLng
import java.util.*

@Composable
fun HistoryScreen(positionAndTimesStateList: State<List<PositionAndTime>>) {
    if (positionAndTimesStateList.value.isNotEmpty()) {
        var lock by remember { mutableStateOf(false) }
        LazyColumn() {
            items(positionAndTimesStateList.value) { pat ->
                Column() {
                    Text(text = "Checked In: " + pat.date)
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "Latitude: " + pat.latitude)
                        Text(text = "Longitude: " + pat.longitude)
                        IconButton(onClick = {
                            lock = !lock
                        }) { //TODO: Needs additional logic on what to do if locked/unlocked
                            if (lock) Icon(
                                painter = painterResource(id = R.drawable.ic_outline_lock_24),
                                contentDescription = null
                            )
                            if (!lock) Icon(
                                painter = painterResource(id = R.drawable.ic_outline_lock_open_24),
                                contentDescription = null
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(text = "Temperature: " + pat.temperature)
                        Text(text = "Weather: " + pat.weather)
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

