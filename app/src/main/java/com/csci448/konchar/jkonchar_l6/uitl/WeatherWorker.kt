package com.csci448.konchar.jkonchar_l6.uitl

import android.content.Context
import android.util.Log
import androidx.work.*
import java.net.URL

class WeatherWorker(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {
    companion object {
        const val UNIQUE_WORK_NAME = "WEATHERWORKER_API_REQUEST"
        private const val LOG_TAG = "448.GEOLOCATR"
        private const val WEATHERMAP_API_KEY = "31b74afce7fdf5c3a4d0654e06eb0301"
        var lat = 1.1 // TODO fixme
        var lon = 180.0


        fun buildOneTimeWorkRequest() = OneTimeWorkRequest
            .Builder(WeatherWorker::class.java)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()

        fun getApiData(outputData: Data) = outputData.getString(WEATHERMAP_API_KEY)
    }
    override fun doWork(): Result {
        Log.d(LOG_TAG, "Work request triggered")
        val apiData = URL("https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${WEATHERMAP_API_KEY}").readText()
        Log.d(LOG_TAG, "Got result $apiData")
        val outputData = workDataOf( WEATHERMAP_API_KEY to apiData )
        return Result.success(outputData)
    }
}

class Weather()