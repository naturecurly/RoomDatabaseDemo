package com.example.leonwu.roomdatabasedemo.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


/**
 * Created by leonwu on 22/6/17.
 */

@Entity
data class Employee(@PrimaryKey(autoGenerate = true)
                    var uid: Int,

                    var firstName: String,

                    var lastName: String,

                    var birthday: Date = Date()
) {
    override fun toString(): String {
        return "[" + this.uid + "]    " + this.firstName + " " + this.lastName
    }
}

