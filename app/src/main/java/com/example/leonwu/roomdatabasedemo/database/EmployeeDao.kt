package com.example.leonwu.roomdatabasedemo.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by leonwu on 22/6/17.
 */

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

    @Query("SELECT * FROM employee WHERE uid IN (:p0)")
    fun loadAllByIds(ids: Array<Int>): List<Employee>

    @Query("SELECT * FROM employee WHERE firstName LIKE :p0 AND " + "lastName LIKE :p1 LIMIT 1")
    fun findByName(first: String, last: String): Employee

    @Insert
    fun insertAll(vararg employee: Employee)

    @Delete
    fun delete(employee: Employee)
}