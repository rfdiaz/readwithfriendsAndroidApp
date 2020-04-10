package com.readwithfriends.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> AppCompatActivity.getViewModel(cl: Class<T>, viewModelCallback: T.() -> Unit): T {
    return ViewModelProviders.of(this).get(cl).apply {
        viewModelCallback(this)
    }
}