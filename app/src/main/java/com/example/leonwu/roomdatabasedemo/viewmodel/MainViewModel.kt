package com.example.leonwu.roomdatabasedemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

/**
 * Created by leonwu on 11/7/17.
 */
class MainViewModel : ViewModel() {
    var data: ObservableField<String> = ObservableField<String>()


}