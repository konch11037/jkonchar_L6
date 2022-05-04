package com.csci448.konchar.jkonchar_l6.uitl

import kotlin.text.StringBuilder
import android.content.pm.PackageManager
import android.os.Looper
import com.google.android.gms.location.LocationRequest
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.csci448.konchar.jkonchar_l6.R
import com.csci448.konchar.jkonchar_l6.viewmodels.I_GeoLocatrViewModel
import java.io.IOException

class LocationUtility(activity: ComponentActivity, viewModel: I_GeoLocatrViewModel) {
    // Values
    private val LOG_TAG = "L_6_LocationUtility"

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    private val permissionLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            isGrantedMap ->
            var hasEnoughPermissions: Boolean = false
            isGrantedMap.forEach{ (perm, isGranted) ->
                hasEnoughPermissions = hasEnoughPermissions || isGranted
            }
            if (hasEnoughPermissions)
                getLocation()
    }
    private val locationRequest = LocationRequest.create().apply{
        interval = 0
        numUpdates = 1
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    // Methods
//    init {
//        val factory = GeoLocatrViewModelFactory()
//        viewModel = ViewModelProvider(activity, factory)[factory.getViewModelClass()]
//    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            // location received in locationResult.lastLocatio
            Log.d(LOG_TAG, "Got a location: ${locationResult.lastLocation}")
            viewModel.currentLocationLiveData.value = locationResult.lastLocation
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    public fun checkPermissionAndGetLocation(activity: ComponentActivity) {
        if (
            activity.checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
            activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            Log.d(LOG_TAG, "User Granted Location Permissions")
            getLocation()
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(LOG_TAG, "User DID NOT GRANT Location Permissions: Permission Denied")
                Toast.makeText(activity, R.string.location_reason_message, Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                Log.d(LOG_TAG, "Requesting Permission Services from User")
                permissionLauncher.launch(
                    arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    private fun getAddress(context: Context, location: Location?): String {
        if (location == null) return ""

        val geocoder = Geocoder(context)
        val addressTextBuilder = StringBuilder()
        try {
            val addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
        1)
            if (
                addresses != null &&
                addresses.isNotEmpty()
            ) {
                val address = addresses[0]
                for (i in 0..address.maxAddressLineIndex) {
                    if (i>0) addressTextBuilder.append("\n")
                    addressTextBuilder.append(address.getAddressLine(i))
                }
            }
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Error getting address: ${e.localizedMessage}")
        }
        return addressTextBuilder.toString()
    }

    public fun getAddressForCurrentLocation(context: Context, viewModel: I_GeoLocatrViewModel) {
        viewModel.currentAddressLiveData.value =
            getAddress(context, viewModel.currentLocationLiveData.value)
    }

    public fun removeLocationRequest() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}