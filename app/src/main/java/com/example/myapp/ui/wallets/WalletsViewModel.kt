package com.example.myapp.ui.wallets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WalletsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Wallets Fragment"
    }
    val text: LiveData<String> = _text
}