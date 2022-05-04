package com.csci448.konchar.jkonchar_l6.uitl

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkConnectionUtil {
    @SuppressLint("MissingPermission")
    fun isNetworkAvailableAndConnected(context: Context) : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetwork

        return activeNetwork != null
                &&
                (cm
                    .getNetworkCapabilities(activeNetwork)
                    ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    ?: false)
    }
}