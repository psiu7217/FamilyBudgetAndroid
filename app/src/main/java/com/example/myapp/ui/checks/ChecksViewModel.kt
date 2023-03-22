package com.example.myapp.ui.checks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChecksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is checks Fragment"
    }
    val text: LiveData<String> = _text
}