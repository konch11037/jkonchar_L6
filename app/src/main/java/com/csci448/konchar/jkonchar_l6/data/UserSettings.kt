package com.csci448.konchar.jkonchar_l6.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "UserSettings")
class UserSettings (
    val saveLocation: Boolean = true,
    @PrimaryKey
    val userID : UUID = UUID.randomUUID()
    )