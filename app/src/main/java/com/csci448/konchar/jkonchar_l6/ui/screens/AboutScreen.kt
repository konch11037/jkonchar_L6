package com.csci448.konchar.jkonchar_l6.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.csci448.konchar.jkonchar_l6.R

@Composable
fun AboutScreen(){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
       Text(text = stringResource(R.string.app_name), fontSize = 40.sp)
        Spacer(Modifier.height(20.dp))
        Text(text = "Developed By", fontWeight = FontWeight.Bold)
        Text("Madness Studios")
        Spacer(Modifier.height(20.dp))
        Text(text = "Version", fontWeight = FontWeight.Bold)
        Text("Alpha - 0.0.1")
        Spacer(Modifier.height(30.dp))
        Text(text = "About", fontWeight = FontWeight.Bold)
        Text(text = "This app will query the weather at your current location when you press the Floating Action Button" +
                "(FAB). Your location is plotted on to a map.  Clicking on the marker will display the time that you checked in" +
                "at that location and the weather at the time of check in.  This information is presented to you in the form of a snackbar" +
                "at the bottom of the screen. You will also be able to view a history of places you checked in at and you can choose to save" +
                "or discard any given location.", textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewAboutScreen(){
    AboutScreen()
}