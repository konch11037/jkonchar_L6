package com.csci448.konchar.jkonchar_l6

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GeoLocatrViewModel:  ViewModel() {
    val currentLocationLiveData: MutableLiveData<Location?> =
                                    MutableLiveData<Location?>(null)
    val currentAddressLiveData: MutableLiveData<String?> =
                                    MutableLiveData<String?>("")




}