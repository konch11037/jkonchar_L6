package com.csci448.konchar.jkonchar_l6.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.csci448.konchar.jkonchar_l6.R
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import com.google.android.material.slider.Slider


@Composable
fun SettingsScreen(viewModel: I_GeoLocatrViewModel) {
    Column(Modifier.fillMaxSize()) {
        Text(color = Color.Magenta, text = "Location Database")
        Row(Modifier.fillMaxWidth().padding(8.dp), Arrangement.SpaceBetween) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_save_24),
                contentDescription = null
            )
            Text("Save Locations to Database")
            var checkedState = viewModel.getUserSettings().observeAsState(UserSettings())


            Spacer(Modifier.width(20.dp))
            Switch(checked = checkedState.value.saveLocation, onCheckedChange = {
                checkedState.value.saveLocation = it
                if (checkedState.value.saveLocation) viewModel.setLocationSaving_ON_UserSettings() else viewModel.setLocationSaving_OFF_UserSettings()
            })
        }
        Row(
            Modifier
                .fillMaxWidth().padding(8.dp)
                .clickable { viewModel.deleteAll() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            Spacer(Modifier.width(42.dp))
            Text("Delete Database")
        }

    }

}
