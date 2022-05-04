package com.csci448.konchar.jkonchar_l6.data

import androidx.room.PrimaryKey
import java.util.*

class UserSettings {
    val saveLocation: Boolean = false
    @PrimaryKey
    val userID = UUID.randomUUID()
}