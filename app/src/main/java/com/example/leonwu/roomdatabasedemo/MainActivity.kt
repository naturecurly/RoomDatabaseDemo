package com.example.leonwu.roomdatabasedemo

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.leonwu.roomdatabasedemo.controller.EmployeeController
import com.example.leonwu.roomdatabasedemo.database.Employee
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import com.example.leonwu.roomdatabasedemo.injection.ActivityModule
import com.example.leonwu.roomdatabasedemo.injection.DaggerActivityComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger {

    @Inject
    lateinit var db: EmployeeDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseDagger()
        val controller: EmployeeController = EmployeeController(db)
        controller.addEmployees(Employee(0, "leon", "wu"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("test", "test")
                })
    }

    //
    fun initialiseDagger() {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }


}
