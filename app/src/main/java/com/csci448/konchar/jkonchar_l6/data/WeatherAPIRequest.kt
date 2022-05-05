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
    val key = "97bfa91b4d6625c24437ea05c2af5625"
    val oldkey = "31b74afce7fdf5c3a4d0654e06eb0301"
    val request: Request = Request.Builder()
        .url("https://api.openweathermap.org/data/2.5/weather?lat=${lat.toInt()}&lon=${long.toInt()}&appid=${key}&units=imperial")
        .get()
        .build()
    //same as above, this function executes on IO dispatcher. Won't block UI thread
    val response = client.newCall(request).execute()
    apiData = response.body?.string()
    if (apiData != null) {
        Log.d(LOG_TAG, "Got result $apiData")
        Log.d(LOG_TAG, "Length ${apiData.length}")
    }
    parseWeatherJSON(apiData, pat)
}
fun parseWeatherJSON(
    apiData: String?,
    pat: PositionAndTime,
) {

    val LOG_TAG = "GEOLOCATR"
    Log.d(LOG_TAG, "parseRecipeJSON() function called")
    Log.d(LOG_TAG, "apiData contains $apiData")
    val properties = JSONObject(apiData)

    val jsonArr1 = checkNotNull(properties.getJSONArray("weather"))
    val jsonObj = checkNotNull(properties.getJSONObject("main"))

    for(i in 0 until jsonArr1.length()){
        pat.weather = jsonArr1.getJSONObject(i).getString("description")

    }

        pat.temperature = jsonObj.getString("temp")

    Log.d(LOG_TAG, "Weather = ${pat.weather}")
    Log.d(LOG_TAG, "Temp = ${pat.temperature}")


}
