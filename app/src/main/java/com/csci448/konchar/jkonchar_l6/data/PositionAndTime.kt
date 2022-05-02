package com.csci448.konchar.jkonchar_l6.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
class PositionAndTime (
    val latLng      : LatLng,
    val dateTime    : LocalDateTime
): Serializable
{
    @PrimaryKey
    var id: UUID = UUID.randomUUID()
}