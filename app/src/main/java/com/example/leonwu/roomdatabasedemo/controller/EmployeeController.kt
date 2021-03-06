package com.example.leonwu.roomdatabasedemo.controller

import android.arch.lifecycle.LiveData
import com.example.leonwu.roomdatabasedemo.database.Employee
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers

/**
 * Created by leonwu on 22/6/17.
 */
class EmployeeController(private val db: EmployeeDatabase) {
    fun addEmployees(employee: Employee): Observable<Unit> {
        return Observable.create<Unit> { e ->
            try {
                db.employeeDao().insertAll(employee)
                e.onNext(Unit)
                e.onComplete()
            } catch (exception: Exception) {
                e.onError(exception)
            }
        }.subscribeOn(Schedulers.io())
    }

    fun getAllEmployeeLiveData(): LiveData<List<Employee>> {
        return db.employeeDao().getAll()
    }

    fun getEmployeeById(id: Int): Observable<List<Employee>> {
        return Observable.create(object : ObservableOnSubscribe<List<Employee>> {
            override fun subscribe(e: ObservableEmitter<List<Employee>>) {
                val list = db.employeeDao().loadAllByIds(arrayOf(id))
                e.onNext(list)
                e.onComplete()
            }

        }).subscribeOn(Schedulers.io())
    }

    fun getEmployeeByName(first: String, last: String): Observable<Employee> {
        return Observable.create<Employee> { e ->
            try {
                val employee = db.employeeDao().findByName(first, last)
                employee?.let {
                    e.onNext(employee)
                    e.onComplete()
                }
            } catch (ex: Exception) {
                e.onError(ex)
            }
        }.subscribeOn(Schedulers.io())
    }

    fun deleteEmployee(employee: Employee): Observable<Unit> {
        return Observable.create<Unit> { e ->
            try {
                db.employeeDao().delete(employee)
                e.onNext(Unit)
                e.onComplete()
            } catch (exception: Exception) {
                e.onError(exception)
            }
        }.subscribeOn(Schedulers.io())
    }
}