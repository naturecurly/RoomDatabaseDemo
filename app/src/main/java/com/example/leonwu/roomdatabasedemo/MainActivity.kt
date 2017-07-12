package com.example.leonwu.roomdatabasedemo

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.leonwu.roomdatabasedemo.controller.EmployeeController
import com.example.leonwu.roomdatabasedemo.database.Employee
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import com.example.leonwu.roomdatabasedemo.databinding.ActivityMainBinding
import com.example.leonwu.roomdatabasedemo.injection.ActivityModule
import com.example.leonwu.roomdatabasedemo.injection.DaggerActivityComponent
import com.example.leonwu.roomdatabasedemo.viewmodel.MainViewModel
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

class MainActivity : AppCompatActivity(), AnkoLogger, LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialiseDagger()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
        viewModel.employeeLiveData.observe(this, Observer { list ->
            list?.let {
                viewModel.setStatus(list)
            }
        })
    }

    fun initialiseDagger() {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }
}
