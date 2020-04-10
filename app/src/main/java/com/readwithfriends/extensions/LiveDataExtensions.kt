package com.readwithfriends.extensions

import androidx.lifecycle.*

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, predicate: (T) -> Unit) {
    this.observe(lifecycleOwner, Observer { predicate(it) })
}

fun <T> LiveData<T>.observeOnce(predicate: (T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            predicate(t)
            removeObserver(this)
        }
    })
}

fun <T, R> LiveData<T>.transform(predicate: (T) -> R): MutableLiveData<R> {
    return Transformations.switchMap(this) { t ->
        MutableLiveData<R>().apply { value = predicate(t) }
    } as MutableLiveData<R>
}

fun <T, R> LiveData<T?>.transformNotNull(predicate: (T) -> R): MutableLiveData<R> {
    return Transformations.switchMap(this) { t ->
        MutableLiveData<R>().apply { if (t != null) value = predicate(t) }
    } as MutableLiveData<R>
}