package com.csci448.konchar.jkonchar_l6.data

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

fun makeApiWeatherRequest(pat: PositionAndTime) {
    val LOG_TAG = "GEOLOCATR"
    Log.d(LOG_TAG, "makeApiRecipeRequest() function called")
    val client = OkHttpClient()
    val apiData: String?

    val lat = pat.latitude
    val long = pat.longitude
    val request: Request = Request.Builder()
        .url("https://api.openweathermap.org/data/2.5/weather?lat=${lat.toInt()}&lon=${long.toInt()}&appid={31b74afce7fdf5c3a4d0654e06eb0301}&units=imperial")
        .get()
        .build()
    //same as above, this function executes on IO dispatcher. Won't block UI thread
    val response = client.newCall(request).execute()
    apiData = response.body?.string()
    if (apiData != null) {
        Log.d(LOG_TAG, "Got result $apiData")
        Log.d(LOG_TAG, "Length ${apiData.length}")
    }
    parseRecipeJSON(apiData, pat)
}
fun parseRecipeJSON(
    apiData: String?,
    pat: PositionAndTime,
) {

    val LOG_TAG = "GEOLOCATR"
    Log.d(LOG_TAG, "parseRecipeJSON() function called")
    Log.d(LOG_TAG, "apiData contains $apiData")
    val properties = JSONObject(apiData)
    pat.weather = checkNotNull(properties.getString("weather.description"))
    pat.temperature = checkNotNull(properties.getString("main.temp"))
    Log.d(LOG_TAG, "Weather = ${pat.weather}")
    Log.d(LOG_TAG, "Temp = ${pat.temperature}")


}
