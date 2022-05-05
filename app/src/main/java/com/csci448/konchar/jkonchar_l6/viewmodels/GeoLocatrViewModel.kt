package com.csci448.konchar.jkonchar_l6.viewmodels

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import com.csci448.konchar.jkonchar_l6.data.database.PositionAndTimeRepository
import com.csci448.konchar.jkonchar_l6.uitl.WeatherWorker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.*

class GeoLocatrViewModel(
    private val positionAndTimeRepository: PositionAndTimeRepository,
    context: Context
   ): I_GeoLocatrViewModel(){
    override val currentLocationLiveData: MutableLiveData<Location?> =
                                    MutableLiveData<Location?>(null)
    override val currentAddressLiveData: MutableLiveData<String?> =
                                    MutableLiveData<String?>("")
    private val workManager = WorkManager.getInstance(context)
    private val workRequest = WeatherWorker.buildOneTimeWorkRequest()
    val outputWorkerInfo: LiveData<WorkInfo> =
        workManager.getWorkInfoByIdLiveData(workRequest.id)

    private val _positionaAndTimeIdLiveData=MutableLiveData<UUID>()

    override val positionAndTimeListLiveData  = positionAndTimeRepository.getPositionAndTimes()

    override val positionAndTimeLiveData =
        Transformations.switchMap(_positionaAndTimeIdLiveData) { id ->
            positionAndTimeRepository.getPositionAndTime(id)
        }

    override fun deleteAll() = positionAndTimeRepository.deletePositionAndTimes()

    override fun deletePositionAndTime(id: UUID) = positionAndTimeRepository.deletePositionAndTime(id)

    override fun addPositionAndTime(positionAndTime: PositionAndTime) {
       positionAndTimeRepository.addPositionAndTime(positionAndTime)
    }
    override fun getPositionAndTimes(): LiveData<List<PositionAndTime>> = positionAndTimeRepository.getPositionAndTimes()

    override fun getPositionAndTime(id: UUID): LiveData<PositionAndTime> = positionAndTimeRepository.getPositionAndTime(id)

    fun getUserSettings(): LiveData<UserSettings> {
        return positionAndTimeRepository.getUserSettings()
    }

}