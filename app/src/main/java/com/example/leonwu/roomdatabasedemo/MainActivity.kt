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
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger {

    @Inject
    lateinit var db: EmployeeDatabase

    @Inject
    lateinit var controller: EmployeeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseDagger()
        btn_add.onClick { view ->
            controller.addEmployees(Employee(0, first_name_edit_text.text.toString(), last_name_edit_text.text.toString()))
                    .flatMap { unit ->
                        controller.getAllEmployees()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        updateStatus(list)
                    })
        }
    }

    fun initialiseDagger() {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    fun updateStatus(list: List<Employee>) {
        val sb: StringBuilder = StringBuilder()
        for (em in list) {
            sb.append(em.toString())
            sb.append("\n")
        }
        database_status.text = sb.toString()
    }

}
