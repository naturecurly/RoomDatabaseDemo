package com.example.leonwu.roomdatabasedemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.ObservableField
import android.util.Log
import com.example.leonwu.roomdatabasedemo.R
import com.example.leonwu.roomdatabasedemo.controller.EmployeeController
import com.example.leonwu.roomdatabasedemo.database.Employee
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by leonwu on 11/7/17.
 */
class MainViewModel(val context: Context, val controller: EmployeeController, val employeeLiveData: LiveData<List<Employee>>) : ViewModel() {
    var data: ObservableField<String> = ObservableField<String>()
    var firstname: ObservableField<String> = ObservableField<String>()
    var lastname: ObservableField<String> = ObservableField<String>()
    var queryId: ObservableField<String> = ObservableField<String>()
    var queryResult: ObservableField<String> = ObservableField<String>()

    fun setStatus(list: List<Employee>) {
        val sb = StringBuilder()
        list.let {
            for (item in list) {
                sb.append("${item.uid}  ${item.firstName} ${item.lastName}\n")
            }
        }
        data.set(sb.toString())
    }

    fun add() {
        controller.addEmployees(Employee(0, firstname.get() ?: "", lastname.get() ?: ""))
                .map { unit ->
                    controller.getAllEmployeeLiveData()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {}, {
                    Log.d("complete", "complete")
                })
    }

    fun delete() {
        controller.getEmployeeByName(firstname.get() ?: "", lastname.get() ?: "")
                .flatMap { controller.deleteEmployee(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //automatically update
                })
    }

    fun query() {
        controller.getEmployeeById(queryId.get().toInt())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->

                    val sb: StringBuilder = StringBuilder()
                    for (em in list) {
                        sb.append("\n")
                        sb.append(em.toString())
                        sb.append("\n")
                    }
                    if (list.size == 0) {
                        queryResult.set(context.getString(R.string.no_result))
                    } else {
                        queryResult.set(sb.toString())
                    }
                }, {}, {

                })
    }

    class Factory @Inject constructor(private val context: Context, private val controller: EmployeeController) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
            val viewModel = MainViewModel(context, controller, controller.getAllEmployeeLiveData())
            return viewModel as T
        }

    }
}