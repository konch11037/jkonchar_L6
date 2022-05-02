package com.csci448.konchar.jkonchar_l6.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.csci448.konchar.jkonchar_l6.data.PositionAndTime

@Database(entities = [PositionAndTime::class], version = 1)
@TypeConverters(PositionTimeTypeConverter::class)
abstract class PositionAndTimeDatabase : RoomDatabase() {
   companion object {
        @Volatile private var INSTANCE: PositionAndTimeDatabase? = null
        fun getInstance(context: Context): PositionAndTimeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context, PositionAndTimeDatabase::class.java,
                        "positionAndTime-database").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }


   abstract val positionAndTimeDAO: PositionAndTimeDAO
}