package com.csci448.konchar.jkonchar_l6.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import java.util.*

@Dao
interface PositionAndTimeDAO {
    @Insert
    fun addPositionAndTime(paT: PositionAndTime)

    @Query("SELECT * FROM PositionAndTime")
    fun getPositionAndTimes(): LiveData<List<PositionAndTime>>

    @Query("SELECT * FROM PositionAndTime WHERE id=(:id)")
    fun getPositionAndTime(id: UUID): LiveData<PositionAndTime>

    @Query("DELETE FROM PositionAndTime")
    fun deleteAll()

    @Query("DELETE FROM PositionAndTime where id = :id")
    fun deletePositionAndTime(id: UUID)

    @Insert
    fun addUserSettings(userSettings: UserSettings)


    @Query("SELECT * FROM UserSettings")
    fun getUserSettings(): LiveData<UserSettings>
}