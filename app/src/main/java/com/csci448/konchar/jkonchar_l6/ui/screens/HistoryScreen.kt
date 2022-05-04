package com.csci448.konchar.jkonchar_l6.ui.screens

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Card(Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column() {
                    Text(fontSize = 12.sp, text = "Checked In: " + pat.date)
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(fontSize = 12.sp, text = "Latitude: " + pat.latitude)
                        Spacer(Modifier.width(12.dp))
                        Text(fontSize = 12.sp, text = "Longitude: " + pat.longitude)
                    }

                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text( fontSize = 12.sp, text = "Temperature: " + pat.temperature)
                        Spacer(Modifier.width(12.dp))
                        Text(fontSize = 12.sp, text = "Weather: " + pat.weather)
                    }
                }
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
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

