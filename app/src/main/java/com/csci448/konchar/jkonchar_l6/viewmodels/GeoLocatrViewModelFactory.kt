package com.csci448.konchar.jkonchar_l6.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csci448.konchar.jkonchar_l6.data.database.PositionAndTimeRepository

class GeoLocatrViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    fun getViewModelClass() = GeoLocatrViewModel::class.java
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if( modelClass.isAssignableFrom(getViewModelClass()) )
            return modelClass
                .getConstructor(PositionAndTimeRepository::class.java, Context::class.java)
                .newInstance(PositionAndTimeRepository.getInstance(context), context)
        throw IllegalArgumentException("Unknown ViewModel")
    }
}