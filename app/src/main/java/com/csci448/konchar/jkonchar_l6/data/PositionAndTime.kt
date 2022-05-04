package com.csci448.konchar.jkonchar_l6.data

import android.service.controls.templates.TemperatureControlTemplate
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
class PositionAndTime (
    var longitude      : Float,
    var latitude       : Float,
    var temperature     : String,
    var weather         : String,
    val date           : Date
): Serializable
{
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
}