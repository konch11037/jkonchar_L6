package com.csci448.konchar.jkonchar_l6.uitl

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    companion object{
        private const val DATA_STORE_NAME = "preferences"
        private val Context.dataStore by preferencesDataStore(
            name = DATA_STORE_NAME
        )
        private val USER_SAVE_LOCATIONS = booleanPreferencesKey("save_location")
    }

    val doNotificationFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[USER_SAVE_LOCATIONS] ?: false
        }
    suspend fun setDoNotification(bool: Boolean){
        context.dataStore.edit{
            it[USER_SAVE_LOCATIONS] = bool
        }
    }
}