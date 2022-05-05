package com.csci448.konchar.jkonchar_l6.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime
import com.csci448.konchar.jkonchar_l6.data.UserSettings
import com.csci448.konchar.jkonchar_l6.data.makeApiWeatherRequest
import java.util.*
import java.util.concurrent.Executors

class PositionAndTimeRepository
private constructor(private val positionAndTimeDao: PositionAndTimeDAO){

    companion object {
        @Volatile
        private var INSTANCE: PositionAndTimeRepository? = null
        fun getInstance(context: Context): PositionAndTimeRepository {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    val database= PositionAndTimeDatabase.getInstance(context = context)
                    instance = PositionAndTimeRepository(database.positionAndTimeDAO)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private val executor = Executors.newSingleThreadExecutor()


    fun addPositionAndTime(paT: PositionAndTime) {
        executor.execute {
            makeApiWeatherRequest(paT)
            positionAndTimeDao.addPositionAndTime(paT = paT)
        }
    }

    fun getPositionAndTimes(): LiveData<List<PositionAndTime>> = positionAndTimeDao.getPositionAndTimes()
    fun getPositionAndTime(id: UUID): LiveData<PositionAndTime> = positionAndTimeDao.getPositionAndTime(id)

    fun deletePositionAndTime(id: UUID)=
        executor.execute {
            positionAndTimeDao.deletePositionAndTime(id)
        }

    fun deletePositionAndTimes() {
        executor.execute {
            positionAndTimeDao.deleteAll()
        }
    }

    private fun makeUserSettings() {

            val userSettings = UserSettings()
            positionAndTimeDao.addUserSettings(userSettings)

    }
    fun getUserSettings(): LiveData<UserSettings> {
        executor.execute {
            if (positionAndTimeDao.checkIfSettingsExist() == 0 ) {
                makeUserSettings()
            }
        }
        return positionAndTimeDao.getUserSettings()
    }

    fun setLocationSaving_OFF_UserSettings() {
        executor.execute {
            positionAndTimeDao.setLocationSaving_OFF_UserSettings()
        }
    }

    fun setLocationSaving_ON_UserSettings() {
        executor.execute {
            positionAndTimeDao.setLocationSaving_ON_UserSettings()
        }
    }

}
