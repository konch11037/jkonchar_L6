package com.csci448.konchar.jkonchar_l6.data.database

import androidx.room.TypeConverter
import java.util.*

class PositionTimeTypeConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()

    @TypeConverter
    fun toUUID(stringy: String?) = UUID.fromString(stringy)
}