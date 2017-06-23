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

    fun getAllEmployees(): Observable<List<Employee>> {
        return Observable.create(object : ObservableOnSubscribe<List<Employee>> {
            override fun subscribe(e: ObservableEmitter<List<Employee>>?) {
                try {
                    val list = db.employeeDao().getAll()
                    e?.onNext(list)
                    e?.onComplete()
                } catch (exception: Exception) {
                    e?.onError(exception)
                }
            }

        }).subscribeOn(Schedulers.io())
    }

//    fun getEmployeeById(id: Int): Observable<List<Employee>>? {
//        return Observable.create(object :ObservableOnSubscribe<List<Employee>>{
//            override fun subscribe(e: ObservableEmitter<Employee>?) {
//                db.employeeDao().loadAllByIds(arrayOf(id))
//                e?.onNext()
//                e?.onComplete()
//            }
//
//        })
//    }
}