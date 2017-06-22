package com.example.leonwu.roomdatabasedemo.controller

import com.example.leonwu.roomdatabasedemo.database.Employee
import com.example.leonwu.roomdatabasedemo.database.EmployeeDatabase
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by leonwu on 22/6/17.
 */
class EmployeeController(private val db: EmployeeDatabase) {
    fun addEmployees(employee: Employee): Observable<Unit> {
//        return Observable.fromCallable {
//            db.employeeDao().insertAll(employee)
//        }.subscribeOn(Schedulers.io())
        return Observable.create<Unit> { e ->
            db.employeeDao().insertAll(employee)
            e.onNext(Unit)
            e.onComplete()
        }.subscribeOn(Schedulers.io())
    }
}