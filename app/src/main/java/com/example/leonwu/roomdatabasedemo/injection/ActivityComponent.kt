package com.example.leonwu.roomdatabasedemo.injection

import com.example.leonwu.roomdatabasedemo.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by leonwu on 22/6/17.
 */
@Singleton
@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
}