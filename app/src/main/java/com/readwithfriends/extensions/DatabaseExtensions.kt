package com.readwithfriends.extensions

import android.os.Handler
import android.os.Looper
import kotlin.concurrent.thread

class DbTask<T> private constructor(var task: (() -> T)?, var result: ((T) -> Unit)?) {
    constructor() : this(null, null)

    fun task(task: () -> T): DbTask<T> = apply { this.task = task }
    fun result(result: (T) -> Unit): DbTask<T> = apply { this.result = result }
    fun run() {
        if (task != null) {
            thread {
                val value = task!!()
                uiThread {
                    if (result != null) result!!(value)
                }
            }
        }
    }
}

fun <T> dbTask(): DbTask<T> = DbTask()

inline fun uiThread(crossinline callback: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        callback()
    }
}