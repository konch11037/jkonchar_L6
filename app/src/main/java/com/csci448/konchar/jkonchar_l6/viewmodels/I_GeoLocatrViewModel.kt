package com.csci448.konchar.jkonchar_l6.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import com.csci448.konchar.jkonchar_l6.data.database.PositionAndTimeRepository
import java.util.*

abstract class I_GeoLocatrViewModel: ViewModel() {

    abstract val currentLocationLiveData: MutableLiveData<Location?>
    abstract val currentAddressLiveData: MutableLiveData<String?>
    abstract val positionAndTimeListLiveData: LiveData<List<PositionAndTime>>
    abstract val positionAndTimeLiveData: LiveData<PositionAndTime>
    abstract fun deleteAll()
    abstract fun deletePositionAndTime(id: UUID)
    abstract fun addPositionAndTime(positionAndTime: PositionAndTime)
    abstract fun getPositionAndTimes(): LiveData<List<PositionAndTime>>
    abstract fun getPositionAndTime(id: UUID): LiveData<PositionAndTime>
    abstract fun setLocationSaving_OFF_UserSettings()
    abstract fun getUserSettings(): LiveData<UserSettings>
    abstract fun setLocationSaving_ON_UserSettings()
    abstract var tempy: PositionAndTime
    abstract fun testDelete(paT: PositionAndTime)
}