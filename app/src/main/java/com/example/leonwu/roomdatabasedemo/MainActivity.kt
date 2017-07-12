package com.example.leonwu.roomdatabasedemo

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.leonwu.roomdatabasedemo.controller.EmployeeController
import com.example.leonwu.roomdatabasedemo.database.Employee
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import com.example.leonwu.roomdatabasedemo.databinding.ActivityMainBinding
import com.example.leonwu.roomdatabasedemo.injection.ActivityModule
import com.example.leonwu.roomdatabasedemo.injection.DaggerActivityComponent
import com.example.leonwu.roomdatabasedemo.viewmodel.MainViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.sdk25.coroutines.onClick
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger, LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    @Inject
    lateinit var db: EmployeeDatabase

    @Inject
    lateinit var controller: EmployeeController

    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseDagger()
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        showData()
        btn_add.onClick {
            controller.addEmployees(Employee(0, first_name_edit_text.text.toString(), last_name_edit_text.text.toString()))
                    .map { unit ->
                        controller.getAllEmpolyeesLiveData()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        //                        liveData ->
//                        liveData.observe(this@MainActivity, Observer { list ->
//                            viewModel.data = list.toString()
//                        })
                    }, {}, {
                        Log.d("complete", "complete")
                    })
        }

        btn_query_by_id.onClick {
            controller.getEmployeeById(query_edit_text.text.toString().toInt())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        highlightEmployee(list)
                    }, {}, {

                    })
        }

        btn_delete.onClick {
            controller.getEmployeeByName(first_name_edit_text.text.toString(), last_name_edit_text.text.toString())
                    .flatMap { controller.deleteEmployee(it) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        showData()
                    })
        }
    }

    fun showData() {
//        controller.getAllEmployees()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    list ->
//                    updateStatus(list)
//                })
        controller.getAllEmpolyeesLiveData()
                .observe(this, Observer { list ->
                    Log.d("test", list.toString())
                    binding.viewModel.data.set(list.toString())
                })
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
            sb.append("\n")
            sb.append(em.toString())
            sb.append("\n")
        }
        database_status.text = sb.toString()
    }

    fun highlightEmployee(list: List<Employee>) {
        val sb: StringBuilder = StringBuilder()
        for (em in list) {
            sb.append("\n")
            sb.append(em.toString())
            sb.append("\n")
        }
        if (list.size == 0) {
            query_result.text = getString(R.string.no_result)
        } else {
            query_result.text = sb.toString()
        }
    }

}
