package com.example.deliveryherotest.utils

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.disposables.Disposables

object BindingUtils {

    fun <T> toObservable(field: ObservableField<T>): Flowable<T> {
        return Flowable.create({ emitter: FlowableEmitter<T> ->
                field.get()?.let{
                    emitter.onNext(it)
                }

            val callback: OnPropertyChangedCallback = object : OnPropertyChangedCallback() {
                override fun onPropertyChanged(observable: Observable, i: Int) {
                    field.get()?.let{
                        emitter.onNext(it)
                    }
                }
            }
            field.addOnPropertyChangedCallback(callback)
            emitter.setDisposable(Disposables.fromAction { field.removeOnPropertyChangedCallback(callback) })
        }, BackpressureStrategy.BUFFER)
    }

}