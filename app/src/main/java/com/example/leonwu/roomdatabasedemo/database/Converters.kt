package com.example.leonwu.roomdatabasedemo.database

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by leonwu on 22/6/17.
 */
class Converters {
    @TypeConverter
    fun fromTimeStamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

}