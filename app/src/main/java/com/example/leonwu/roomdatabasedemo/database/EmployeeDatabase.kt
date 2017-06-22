package com.example.leonwu.roomdatabasedemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

/**
 * Created by leonwu on 22/6/17.
 */

@Database(entities = arrayOf(Employee::class), version = 3)
@TypeConverters(Converters::class)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}