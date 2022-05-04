package com.csci448.konchar.jkonchar_l6.data.database

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.util.*

class PositionTimeTypeConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()

    @TypeConverter
    fun toUUID(stringy: String?) = UUID.fromString(stringy)


    @TypeConverter
  fun fromDate(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun toDate(date: Date?): Long? {
    return date?.time?.toLong()
  }

}