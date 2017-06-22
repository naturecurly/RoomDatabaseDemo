package com.example.leonwu.roomdatabasedemo.injection

import android.arch.persistence.room.Room
import android.content.Context
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by leonwu on 22/6/17.
 */

@Module
class ActivityModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideDatabase(): EmployeeDatabase{
        return Room.databaseBuilder(context, EmployeeDatabase::class.java, "employee").build()
    }
}